package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.BetPool
import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.data.models.ParticipantDTO
import com.kvad.totalizator.data.models.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Suppress("MagicNumber")
class EventMockService @Inject constructor() : EventService {

    override suspend fun getEvents(): List<Event> = withContext(Dispatchers.IO) {
        val list = mutableListOf<Event>()

        val startRange = 1
        val endRange = 10
        val betpool = BetPool(1500f, 2500f, 100f)
        val participant = ParticipantDTO(
            id = 1,
            name = "Oleg Zaets",
            photoLink = "https",
            Player(50, 50, 50)
        )
        for (i in startRange..endRange) {
            list.add(Event(
                id = i,
                sport = "sport",
                participantdto1 = participant,
                participantdto2 = participant,
                betPool = betpool
            ))
        }

        return@withContext list
    }
}
