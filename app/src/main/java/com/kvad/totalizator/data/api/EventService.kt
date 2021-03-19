package com.kvad.totalizator.data.api

import com.kvad.totalizator.events.EventResponse

interface EventService {
    suspend fun getEvents() : List<EventResponse>
}
