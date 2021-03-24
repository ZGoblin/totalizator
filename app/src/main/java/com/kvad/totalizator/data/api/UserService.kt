package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.requestmodels.AccountInfo
import com.kvad.totalizator.data.requestmodels.BetRequest
import com.kvad.totalizator.data.requestmodels.Token
import com.kvad.totalizator.data.requestmodels.Wallet
import com.kvad.totalizator.data.requestmodels.LoginRequest
import com.kvad.totalizator.data.requestmodels.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @POST("/api/Auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<Token>

    @POST("/api/Auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<Token>

    @GET("/api/Wallet")
    suspend fun wallet(): Response<Wallet>

    @POST("/api/Bet")
    suspend fun doBet(@Body betRequest : BetRequest) : Response<Unit>

    @POST("/api/Account")
    suspend fun accountInfo() : Response<AccountInfo>
}
