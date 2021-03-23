package com.kvad.totalizator.data.api

import com.kvad.totalizator.betfeature.BetRequest
import com.kvad.totalizator.data.requestmodels.*

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @POST("/api/v1/auth/Login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<Token>

    @POST("/api/v1/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<Token>

    @GET("/api/v1/wallet")
    suspend fun wallet(): Response<Wallet>

    @POST("/api/v1/bet")
    suspend fun doBet(@Body betRequest : BetRequest) : Response<Unit>

    @POST("/api/v1/wallet/transaction")
    suspend fun transaction(@Body transactionRequest : TransactionRequest) : Response<Unit>
}
