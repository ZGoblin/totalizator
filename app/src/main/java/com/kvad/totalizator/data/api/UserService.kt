package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.LoginRequest
import com.kvad.totalizator.data.models.RegisterRequest
import com.kvad.totalizator.data.models.Token
import com.kvad.totalizator.data.models.Wallet
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @POST("/api/v1/auth/Login")
    suspend fun login(@Body loginRequest: LoginRequest): Token

    @POST("/api/v1/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Token

    @GET("/api/v1/wallet")
    suspend fun wallet(): Wallet
}
