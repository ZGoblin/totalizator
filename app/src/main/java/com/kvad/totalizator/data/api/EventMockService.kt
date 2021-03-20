package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.BetPool
import com.kvad.totalizator.data.models.Characteristic
import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.data.models.ParticipantDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Suppress("MagicNumber")
class EventMockService : EventService {

    override suspend fun getEvents(): List<Event> = withContext(Dispatchers.IO) {
        val list = mutableListOf<Event>()

        val startRange = 1
        val endRange = 10
        val betpool = BetPool(1500f, 2500f, 100f)
        val participant = ParticipantDTO(
            id = 1,
            name = "Oleg Zaets",
            photoLink = "https"
        )
        for (i in startRange..endRange) {
            list.add(
                Event(
                    id = i,
                    sport = "sport",
                    participantDto1 = participant,
                    participantDto2 = participant,
                    betPool = betpool,
                    setOf(Characteristic("Weight", "1", "2"))
                )
            )
        }

        return@withContext list
    }
}
