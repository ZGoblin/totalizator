package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.Wallet

interface UserService {
    suspend fun getWallet() : Wallet
}
