package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.LoginRequest
import com.kvad.totalizator.data.models.RegisterRequest
import com.kvad.totalizator.data.models.Token
import com.kvad.totalizator.data.models.Wallet
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {

    @POST("/api/Auth/Login")
    suspend fun login(@Body loginRequest: LoginRequest): Token

    @POST("/api/Auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Token

    @POST("/api/Wallet")
    suspend fun wallet(): Wallet
}
