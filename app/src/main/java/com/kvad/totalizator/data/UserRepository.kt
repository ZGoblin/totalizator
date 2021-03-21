package com.kvad.totalizator.data

import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.data.models.LoginRequest
import com.kvad.totalizator.data.models.RegisterRequest
import com.kvad.totalizator.data.models.Token
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.safeApiCall
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService
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
}
