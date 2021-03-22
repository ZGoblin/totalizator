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
        for (i in 1..10) {
            list.add(
                Event(
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
            )
        }

        return@withContext list
    }
}
