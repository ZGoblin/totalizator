package com.kvad.totalizator.data

import com.kvad.totalizator.data.api.EventService
import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.safeApiCall
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventService: EventService,
) {

    suspend fun getEvents(): ResultWrapper<List<Event>> {
        return safeApiCall {
            eventService.getEvents()
        }
    }
}
