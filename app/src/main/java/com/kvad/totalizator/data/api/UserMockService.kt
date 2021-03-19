package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.Wallet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserMockService: UserService {
    companion object {
        private var amount = 100
    }

    override suspend fun getWallet(): Wallet = withContext(Dispatchers.IO) {
        return@withContext Wallet(
            walletId = 1,
            amount = amount++
        )
    }
}
