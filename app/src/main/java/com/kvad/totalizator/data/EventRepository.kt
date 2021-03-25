package com.kvad.totalizator.data

import com.kvad.totalizator.data.api.EventService
import com.kvad.totalizator.data.mappers.MapRequestEventToEvent
import com.kvad.totalizator.data.model.Event
import com.kvad.totalizator.tools.REQUEST_DELAY
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.mapSuccess
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

typealias ListEventWrapper = ApiResultWrapper<List<Event>>

@Singleton
class EventRepository @Inject constructor(
    private val eventService: EventService,
    private val mapRequestEventToEvent: MapRequestEventToEvent
) {
    private val _line: MutableSharedFlow<ListEventWrapper> = MutableSharedFlow(replay = 1)
    val line: SharedFlow<ListEventWrapper> = _line

    init {
        GlobalScope.launch {
            flow {
                while (true) {
                    val line = safeApiCall(eventService::getLine).mapSuccess {
                        mapRequestEventToEvent.map(it.events)
                    }
                    emit(line)
                    delay(REQUEST_DELAY)
                }
            }.collect {
                _line.emit(it)
            }
        }
    }

    fun getEventById(id: String): Flow<ApiResultWrapper<Event>> {
        return line.map {
            if (it.isSuccess()) {
                it.asSuccess().value.find { event ->
                    event.id == id
                }?.let { event ->
                    return@map ApiResultWrapper.Success(event)
                }
                return@map ApiResultWrapper.Error.UnknownError("No event find")
            } else {
                return@map it.asError()
            }
        }
    }
}
