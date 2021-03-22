package com.kvad.totalizator.data.api

import com.kvad.totalizator.betfeature.BetRequest
import com.kvad.totalizator.data.models.*
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

    @POST("/api/v1/bet")
    suspend fun doBet(@Body betRequest : BetRequest)

    @POST("/api/v1/wallet/transaction")
    suspend fun deposit(@Body transaction : TransactionRequest)
}
