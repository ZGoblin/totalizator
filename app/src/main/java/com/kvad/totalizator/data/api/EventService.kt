package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.data.models.Line
import retrofit2.Response
import retrofit2.http.GET

interface EventService {

    suspend fun getEvents() : Response<List<Event>>

    @GET("/api/Events/feed")
    suspend fun getLine() : Response<Line>
}
