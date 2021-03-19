package com.kvad.totalizator.data

import com.kvad.totalizator.data.api.EventService
import com.kvad.totalizator.data.models.Event
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventService: EventService
) {

    suspend fun getEvents(): List<Event> {
        return eventService.getEvents()
    }
}
