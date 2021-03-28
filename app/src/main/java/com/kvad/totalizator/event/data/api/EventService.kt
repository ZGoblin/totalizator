package com.kvad.totalizator.event.data.api

import com.kvad.totalizator.event.data.requestmodels.Line
import com.kvad.totalizator.event.data.requestmodels.RequestEventModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EventService {

    @GET("/api/Events/preview/{id}")
    suspend fun getEvent(
        @Path("id") eventId: String
    ) : Response<RequestEventModel>

    @GET("/api/Events/feed")
    suspend fun getLine() : Response<Line>
}
