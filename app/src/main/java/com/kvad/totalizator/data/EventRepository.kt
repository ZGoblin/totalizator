package com.kvad.totalizator.data

import com.kvad.totalizator.data.api.EventService
import com.kvad.totalizator.data.models.BetPool
import com.kvad.totalizator.data.models.Characteristic
import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.data.models.ParticipantDTO
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.safeApiCall
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class EventRepository @Inject constructor(
    private val eventService: EventService
) {

    suspend fun getEvents(): ResultWrapper<List<Event>> {
        return safeApiCall {
            eventService.getEvents()
        }
    }


    fun setId(id: String): Flow<ResultWrapper<Event>> {
        latestNews = flow {
            while (true) {
                val latestNews = getEventById(id = id)
                emit(latestNews)
                delay(3000)
            }
        }

        return latestNews
    }

    var latestNews: Flow<ResultWrapper<Event>> = flow {}
        private set

    @Suppress("UnusedPrivateMember")
    suspend fun getEventById(id: String): ResultWrapper<Event> {

        val random1 = Random(300).nextFloat()
        val random2 = Random(300).nextFloat()
        val random3 = Random(300).nextFloat()

        return safeApiCall {
            Event(
                id,
                "sport",
                ParticipantDTO(
                    1,
                    "Olexiy",
                    "https://upload.wikimedia.org/wikipedia/commons/a/a9/Olexiy_yurin.jpg",
                    setOf(
                        Characteristic("weight", "55"),
                        Characteristic("height", "55"),
                        Characteristic("age", "55")
                    )
                ),
                ParticipantDTO(
                    1,
                    "Rodion",
                    "https://stuki-druki.com/biofoto/Rodion-Tolokonnikov-Ryadovie.jpg",
                    setOf(
                        Characteristic("weight", "55"),
                        Characteristic("height", "55"),
                        Characteristic("age", "55")
                    )
                ),
                BetPool(random1, random2, random3)
            )
        }
    }
}
