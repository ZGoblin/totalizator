package com.kvad.totalizator.data

import com.kvad.totalizator.data.api.EventService
import com.kvad.totalizator.data.mappers.MapRequestEventToEvent
import com.kvad.totalizator.data.model.Event
import com.kvad.totalizator.tools.REQUEST_DELAY
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.mapSuccess
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val eventService: EventService,
    private val mapRequestEventToEvent: MapRequestEventToEvent
) {

    val lineFlow: Flow<ApiResultWrapper<List<Event>>> = flow {
        while (true) {
            val line = safeApiCall(eventService::getLine).mapSuccess {
                mapRequestEventToEvent.map(it.events)
            }
            emit(line)
            delay(REQUEST_DELAY)
        }
    }

    fun getEventById(id: String): Flow<ApiResultWrapper<Event>> {
        return lineFlow
            .map {
                if (it.isSuccess()) {
                    it.asSuccess().value.find { event ->
                        event.id == id
                    }?.let { event ->
                        return@map ApiResultWrapper.Success(event)
                    }
                }
                return@map it.asError()
            }
    }
}
