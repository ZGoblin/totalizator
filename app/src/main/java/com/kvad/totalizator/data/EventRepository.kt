package com.kvad.totalizator.data

import com.kvad.totalizator.data.api.EventService
import com.kvad.totalizator.data.models.BetPool
import com.kvad.totalizator.data.models.Characteristic
import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.data.models.ParticipantDTO
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.safeApiCall
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventService: EventService
) {

    suspend fun getEvents(): ResultWrapper<List<Event>> {
        return safeApiCall {
            eventService.getEvents()
        }
    }

    @Suppress("UnusedPrivateMember")
    suspend fun getEventById(id: String): ResultWrapper<Event> {
        return safeApiCall {
            return@safeApiCall Event(
                "id",
                "sport",
                ParticipantDTO(
                    1, "Olexiy", "https://upload.wikimedia.org/wikipedia/commons/a/a9/Olexiy_yurin.jpg", setOf(
                        Characteristic("weight", "55"),
                        Characteristic("height", "55"),
                        Characteristic("age", "55")
                    )
                ),
                ParticipantDTO(
                    1, "Rodion", "https://stuki-druki.com/biofoto/Rodion-Tolokonnikov-Ryadovie.jpg", setOf(
                        Characteristic("weight", "55"),
                        Characteristic("height", "55"),
                        Characteristic("age", "55")
                    )
                ),
                BetPool(1F, 1F, 1F)
            )
        }
    }
}
