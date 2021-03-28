package com.kvad.totalizator.accaunt.data

import com.kvad.totalizator.accaunt.data.model.AccountInfo
import com.kvad.totalizator.accaunt.data.model.TransactionRequest
import com.kvad.totalizator.accaunt.data.model.LoginRequest
import com.kvad.totalizator.accaunt.data.model.Wallet
import com.kvad.totalizator.accaunt.data.model.RegisterRequest
import com.kvad.totalizator.beting.bethistory.model.BetHistoryPreview
import com.kvad.totalizator.beting.data.BetRequest
import com.kvad.totalizator.shared.Token
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

    @POST("/api/Wallet")
    suspend fun transaction(@Body transactionRequest : TransactionRequest) : Response<Unit>
  
    @GET("/api/Account/profile")
    suspend fun accountInfo() : Response<AccountInfo>

    @GET("/api/Bet/history")
    suspend fun betHistory() : Response<BetHistoryPreview>

}
