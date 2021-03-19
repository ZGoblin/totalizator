package com.kvad.totalizator.data.api

import com.kvad.totalizator.events.EventResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Suppress("MagicNumber")
class EventMockService @Inject constructor() : EventService {

    override suspend fun getEvents(): List<EventResponse> = withContext(Dispatchers.IO) {
        val list = mutableListOf<EventResponse>()

        val startRange = 1
        val endRange = 10
        for (i in startRange..endRange) {
            list.add(EventResponse(i))
        }

        return@withContext list
    }
}
