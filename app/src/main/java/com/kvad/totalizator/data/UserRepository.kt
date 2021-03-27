package com.kvad.totalizator.data

import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.data.requestmodels.LoginRequest
import com.kvad.totalizator.data.requestmodels.Token
import com.kvad.totalizator.data.requestmodels.TransactionRequest
import com.kvad.totalizator.data.requestmodels.RegisterRequest
import com.kvad.totalizator.data.requestmodels.Wallet
import com.kvad.totalizator.data.requestmodels.AccountInfo
import com.kvad.totalizator.tools.REQUEST_DELAY
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import com.kvad.totalizator.tools.sharedPrefTools.SharedPref
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect

import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userService: UserService,
    private val sharedPref: SharedPref
) {

    private val _wallet: MutableSharedFlow<ApiResultWrapper<Wallet>> = MutableSharedFlow()
    val wallet: SharedFlow<ApiResultWrapper<Wallet>> = _wallet

    init {
        GlobalScope.launch {
            flow {
                while (true) {
                    val response = safeApiCall(userService::wallet)
                    emit(response)
                    delay(REQUEST_DELAY)
                }
            }.collect {
                _wallet.emit(it)
            }
        }
    }

    suspend fun login(loginRequest: LoginRequest): ApiResultWrapper<Token> {
        val result = safeApiCall {
            userService.login(loginRequest)
        }
        updateToken(result)
        return result
    }

    suspend fun register(registerRequest: RegisterRequest): ApiResultWrapper<Token> {
        val result = safeApiCall {
            userService.register(registerRequest)
        }
        updateToken(result)
        return result
    }

    private fun updateToken(result: ApiResultWrapper<Token>) {
        if (result.isSuccess()) {
            sharedPref.token = result.asSuccess().value.token
        }
    }

    suspend fun doTransaction(transactionRequest: TransactionRequest): ApiResultWrapper<Unit> {
        return safeApiCall {
            userService.transaction(transactionRequest)
        }
    }

    suspend fun getLastWallet(): Wallet? {
        val wallet = this.wallet.first()
        if (wallet.isSuccess()) {
            return wallet.asSuccess().value
        }
        return null
    }

    suspend fun accountInfo(): ApiResultWrapper<AccountInfo> {
        return safeApiCall {
            userService.accountInfo()
        }
    }
}
