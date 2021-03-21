package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.LoginRequest
import com.kvad.totalizator.data.models.RegisterRequest
import com.kvad.totalizator.data.models.Token

class UserMockService: UserService {
        companion object {
        private var amount = 100
    }

    override suspend fun login(loginRequest: LoginRequest): Token {
        return Token("good")
    }

    override suspend fun getWallet(): Wallet = withContext(Dispatchers.IO) {
        return@withContext Wallet(
            walletId = 1,
            amount = amount++
        )
    }

    override suspend fun register(registerRequest: RegisterRequest): Token {
        return Token("good")
    }
}
