package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.LoginRequest
import com.kvad.totalizator.data.models.Token

class UserMockService: UserService {
    override suspend fun login(loginRequest: LoginRequest): Token {
        return Token("good")
    }
}
