package com.kvad.totalizator.data

import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.data.requestmodels.LoginRequest
import com.kvad.totalizator.data.requestmodels.Token
import com.kvad.totalizator.data.requestmodels.BetRequest
import com.kvad.totalizator.data.requestmodels.TransactionRequest
import com.kvad.totalizator.data.requestmodels.RegisterRequest
import com.kvad.totalizator.data.requestmodels.Wallet
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
    suspend fun login(loginRequest: LoginRequest): ApiResultWrapper<Token> {
        return safeApiCall {
            userService.login(loginRequest)
        }
    }

    suspend fun register(registerRequest: RegisterRequest): ApiResultWrapper<Token> {
        return safeApiCall {
            userService.register(registerRequest)
        }
    }

    suspend fun wallet(): Flow<ApiResultWrapper<Wallet>> = flow {
        while (true) {
            emit(safeApiCall(userService::wallet))
            delay(REQUEST_DELAY)
        }
    }

    suspend fun doTransaction(transactionRequest: TransactionRequest) : ApiResultWrapper<Unit>{
        return safeApiCall {
            userService.transaction(transactionRequest)
        }
    }

    fun updateToken(token: Token) {
        sharedPref.token = token.token
    }

    suspend fun doBet(betRequest: BetRequest): ApiResultWrapper<Unit>  {
        return safeApiCall {
            userService.doBet(betRequest)
        }
    }

}
