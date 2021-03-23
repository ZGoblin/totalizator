package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.requestmodels.Line
import com.kvad.totalizator.data.requestmodels.RequestEventModel
import retrofit2.Response
import retrofit2.http.GET

interface EventService {

    suspend fun getEvents() : Response<List<RequestEventModel>>

    @GET("/api/Events/feed")
    suspend fun getLine() : Response<Line>
}
