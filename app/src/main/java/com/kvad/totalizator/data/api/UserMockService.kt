package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.Login

class UserMockService: UserService {
    override suspend fun login(login: Login): String {
        return "good"
    }
}
