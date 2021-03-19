package com.kvad.totalizator.data

import com.kvad.totalizator.data.api.EventService
import com.kvad.totalizator.events.EventResponse
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventService: EventService
) {

    suspend fun getEvents(): List<EventResponse> {
        return eventService.getEvents()
    }
}
