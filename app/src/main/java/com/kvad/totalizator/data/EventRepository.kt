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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val eventService: EventService,
    private val mapRequestEventToEvent: MapRequestEventToEvent
) {

    //todo
    suspend fun getLine(): Flow<ApiResultWrapper<List<Event>>> = flow {
        while (true) {
            val line = safeApiCall(eventService::getLine).mapSuccess {
                mapRequestEventToEvent.map(it.events)
            }
            emit(line)
            delay(REQUEST_DELAY)
        }
    }

    fun createEventFlowById(id: String): Flow<ApiResultWrapper<Event>> {
        latestEvent = flow {
            while (true) {
                val latestNews = safeApiCall {
                    eventService.getEvent(id)
                }.mapSuccess(mapRequestEventToEvent::map)

                emit(latestNews)
                delay(REQUEST_DELAY)
            }
        }

        return latestEvent
    }

    var latestEvent: Flow<ApiResultWrapper<Event>> = flow {}
        private set
}
