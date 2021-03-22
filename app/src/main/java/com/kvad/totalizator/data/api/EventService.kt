package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.Event
import retrofit2.Response

interface EventService {
    suspend fun getEvents() : Response<List<Event>>
}
