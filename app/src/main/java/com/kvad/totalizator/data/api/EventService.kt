package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.Event

interface EventService {
    suspend fun getEvents() : List<Event>
}
