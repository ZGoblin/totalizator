package com.kvad.totalizator.data

import com.kvad.totalizator.data.api.EventMockService
import com.kvad.totalizator.data.models.BetPool
import com.kvad.totalizator.data.models.Characteristic
import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.data.models.ParticipantDTO
import com.kvad.totalizator.tools.REQUEST_DELAY
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.safeApiCall

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random


@Singleton
class EventRepository @Inject constructor(
    private val eventService: EventMockService
) {

    suspend fun getEvents(): ApiResultWrapper<List<Event>> {
        return safeApiCall {
            eventService.getEvents()
        }
    }


    fun setIdForEventRequest(id: String) {
        latestEvent = flow {
            while (true) {
                val latestNews = getEventById(id = id)
                emit(latestNews)
                delay(REQUEST_DELAY)
            }
        }
    }

    var latestEvent: Flow<ApiResultWrapper<Event>> = flow {}
        private set

    @Suppress("MagicNumber", "MaxLineLength","UnusedPrivateMember")
    private suspend fun getEventById(id: String): ApiResultWrapper<Event> {

        val random1 = Random.nextFloat() * 100
        val random2 = Random.nextFloat() * 100
        val random3 = Random.nextFloat() * 100

        return safeApiCall {
            Response.success(
                Event(
                    "70ab8247-2b21-42ed-9d90-551adb05b029",
                    "sport",
                    ParticipantDTO(
                        1,
                        "Olexiy",
                        "https://upload.wikimedia.org/wikipedia/commons/1/10/Bundesarchiv_Bild_183-S33882%2C_Adolf_Hitler_retouched.jpg",
                        setOf(
                            Characteristic("weight", "55"),
                            Characteristic("height", "55"),
                            Characteristic("age", "55")
                        )
                    ),
                    ParticipantDTO(
                        1,
                        "Rodion",
                        "https://upload.wikimedia.org/wikipedia/commons/9/9b/CroppedStalin1943.jpg",
                        setOf(
                            Characteristic("weight", "55"),
                            Characteristic("height", "55"),
                            Characteristic("age", "55")
                        )
                    ),
                    BetPool(random1, random2, random3)
                )
            )
        }
    }
}
