package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.LoginRequest
import com.kvad.totalizator.data.models.RegisterRequest
import com.kvad.totalizator.data.models.Token
import com.kvad.totalizator.data.models.Wallet
import retrofit2.http.*

interface UserService {

    @POST("/api/Auth/Login")
    suspend fun login(@Body loginRequest: LoginRequest): Token

    @POST("/api/Auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Token

    @POST("/api/Wallet/{id}")
    suspend fun wallet(
        @Query("id") id: String
    ): Wallet
}
