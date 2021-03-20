package com.kvad.totalizator.data

import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.data.models.Login
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.safeApiCall
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService
) {
    suspend fun login(login: Login) : ResultWrapper<String> {
        return safeApiCall {
            userService.login(login)
        }
    }
}
