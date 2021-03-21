package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.BetPool
import com.kvad.totalizator.data.models.Characteristic
import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.data.models.ParticipantDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// TODO 21.03.2021 clean up
@Suppress("MagicNumber")
class EventMockService : EventService {

    @Suppress("UnusedPrivateMember")
    override suspend fun getEvents(): List<Event> = withContext(Dispatchers.IO) {
        val list = mutableListOf<Event>()

        val startRange = 1
        val endRange = 10
        val betpool = BetPool(1500f, 2500f, 100f)
        val participant = ParticipantDTO(
            id = 1,
            name = "Oleg Zaets",
            photoLink = "https",
            characteristics = setOf(Characteristic("weight", "100"))
        )
        for (i in 1..10) {
            list.add(
                Event(
                    id = "dsfsdf",
                    sport = "sport",
                    participantDto1 = participant,
                    participantDto2 = participant,
                    betPool = betpool
                )
            )
        }

        return@withContext list
    }
}
