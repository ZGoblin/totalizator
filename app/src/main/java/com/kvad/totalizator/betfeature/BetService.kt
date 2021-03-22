package com.kvad.totalizator.betfeature

import retrofit2.http.Body
import retrofit2.http.POST

interface BetService {
    @POST("/api/v1/bet")
    suspend fun doBet(@Body betRequest : BetRequest)
}