package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.requestmodels.Line
import com.kvad.totalizator.data.requestmodels.RequestEventModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventService {

    @GET("/api/Events/preview/{id}")
    suspend fun getEvent(
        @Path("id") eventId: String
    ) : Response<RequestEventModel>

    @GET("/api/Events/feed")
    suspend fun getLine() : Response<Line>
}
