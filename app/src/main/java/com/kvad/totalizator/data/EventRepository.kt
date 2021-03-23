package com.kvad.totalizator.data

import com.kvad.totalizator.data.api.EventService
import com.kvad.totalizator.data.mappers.MapRequestEventToEvent
import com.kvad.totalizator.data.model.Event
import com.kvad.totalizator.data.requestmodels.Characteristic
import com.kvad.totalizator.data.requestmodels.Line
import com.kvad.totalizator.data.requestmodels.Participant
import com.kvad.totalizator.data.requestmodels.RequestEventModel
import com.kvad.totalizator.tools.REQUEST_DELAY
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.mapSuccess
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

    fun setIdForEventRequest(id: String) {
        latestEvent = flow {
            while (true) {
                val latestNews = getEventById(id = id).mapSuccess(mapRequestEventToEvent::map)
                emit(latestNews)
                delay(REQUEST_DELAY)
            }
        }
    }

    var latestEvent: Flow<ApiResultWrapper<Event>> = flow {}
        private set

    @Suppress("MagicNumber", "MaxLineLength", "UnusedPrivateMember")
    private suspend fun getEventById(id: String): ApiResultWrapper<RequestEventModel> {

        val random1 = Random.nextFloat() * 100
        val random2 = Random.nextFloat() * 100
        val random3 = Random.nextFloat() * 100

        return safeApiCall {
            Response.success(
                RequestEventModel(
                    "535d1a2c-bb51-4416-a6d2-f302b99992ad",
                    "sport",
                    Participant(
                        "1",
                        "Olexiy",
                        "https://upload.wikimedia.org/wikipedia/commons/1/10/Bundesarchiv_Bild_183-S33882%2C_Adolf_Hitler_retouched.jpg",
                        setOf(
                            Characteristic("weight", "55"),
                            Characteristic("height", "55"),
                            Characteristic("age", "55")
                        )
                    ),
                    Participant(
                        "1",
                        "Rodion",
                        "https://upload.wikimedia.org/wikipedia/commons/9/9b/CroppedStalin1943.jpg",
                        setOf(
                            Characteristic("weight", "55"),
                            Characteristic("height", "55"),
                            Characteristic("age", "55")
                        )
                    ),
                    "",
                    false,
                    listOf("", ""),
                    0.5f,
                    random1,
                    random2,
                    random3
                )
            )
        }
    }
}
