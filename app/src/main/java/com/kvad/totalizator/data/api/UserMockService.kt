package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.LoginRequest
import com.kvad.totalizator.data.models.RegisterRequest
import com.kvad.totalizator.data.models.Token

class UserMockService: UserService {
    override suspend fun login(loginRequest: LoginRequest): Token {
        return Token("good")
    }

    override suspend fun register(registerRequest: RegisterRequest): Token {
        return Token("good")
    }
}
