package com.kvad.totalizator.data

import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.data.models.Wallet
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService
) {
    suspend fun getWallet(): Wallet {
        return userService.getWallet()
    }
}
