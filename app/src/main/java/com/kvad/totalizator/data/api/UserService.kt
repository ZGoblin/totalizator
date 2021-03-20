package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.Login

interface UserService {
    suspend fun login(login: Login): String
}
