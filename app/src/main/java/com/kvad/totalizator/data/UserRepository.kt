package com.kvad.totalizator.data

import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.data.requestmodels.LoginRequest
import com.kvad.totalizator.data.requestmodels.RegisterRequest
import com.kvad.totalizator.data.requestmodels.Token
import com.kvad.totalizator.data.requestmodels.Wallet
import com.kvad.totalizator.data.requestmodels.AccountInfo
import com.kvad.totalizator.data.requestmodels.BetRequest
import com.kvad.totalizator.tools.REQUEST_DELAY
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import com.kvad.totalizator.tools.sharedPrefTools.SharedPref
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService,
    private val sharedPref: SharedPref
) {
    val wallet: Flow<ApiResultWrapper<Wallet>> = flow {
        while (true) {
            val response = safeApiCall(userService::wallet)
            updateLastWallet(response)
            emit(response)
            delay(REQUEST_DELAY)
        }
    }

    var lastWallet: Wallet? = null
        private set

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

    private fun updateLastWallet(response: ApiResultWrapper<Wallet>) {
        if (response.isSuccess()) {
            lastWallet = response.asSuccess().value
        }
    }

    suspend fun doBet(betRequest: BetRequest): ApiResultWrapper<Unit> {
        return safeApiCall {
            userService.doBet(betRequest)
        }
    }

    suspend fun accountInfo(): ApiResultWrapper<AccountInfo> {
        return safeApiCall {
            userService.accountInfo()
        }
    }
}
