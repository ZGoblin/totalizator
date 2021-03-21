package com.kvad.totalizator.data

import android.util.Log
import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.data.models.LoginRequest
import com.kvad.totalizator.data.models.RegisterRequest
import com.kvad.totalizator.data.models.Token
import com.kvad.totalizator.data.models.Wallet
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.safeApiCall
import com.kvad.totalizator.tools.sharedPrefTools.SharedPref
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService,
    private val sharedPref: SharedPref
) {
    suspend fun login(loginRequest: LoginRequest) : ResultWrapper<Token> {
        return safeApiCall {
            userService.login(loginRequest)
        }
    }

    suspend fun register(registerRequest: RegisterRequest) : ResultWrapper<Token> {
        return safeApiCall {
            userService.register(registerRequest)
        }
    }

    suspend fun wallet() : ResultWrapper<Wallet> {
        return safeApiCall {
            userService.wallet(sharedPref.token)
        }
    }

    fun updateToken(token: Token) {
        sharedPref.token = token.token
    }
}
