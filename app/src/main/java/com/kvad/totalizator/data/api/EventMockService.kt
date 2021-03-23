package com.kvad.totalizator.data.api

import com.kvad.totalizator.data.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

//// TODO 21.03.2021 clean up
//@Suppress("MagicNumber")
//class EventMockService @Inject constructor() : EventService {
//
//    @Suppress("UnusedPrivateMember")
//    override suspend fun getEvents(): Response<List<Event>> = withContext(Dispatchers.IO) {
//        val list = mutableListOf<Event>()
//        for (i in 1..10) {
//            list.add(
//                Event(
//                    "id",
//                    "sport",
//                    ParticipantDTO(
//                        1, "Olexiy", "https://upload.wikimedia.org/wikipedia/commons/a/a9/Olexiy_yurin.jpg", setOf(
//                            Characteristic("weight", "55"),
//                            Characteristic("height", "55"),
//                            Characteristic("age", "55")
//                        )
//                    ),
//                    ParticipantDTO(
//                        1, "Rodion", "https://stuki-druki.com/biofoto/Rodion-Tolokonnikov-Ryadovie.jpg", setOf(
//                            Characteristic("weight", "55"),
//                            Characteristic("height", "55"),
//                            Characteristic("age", "55")
//                        )
//                    ),
//                    BetPool(1F, 1F, 1F)
//                )
//            )
//        }
//
//        return@withContext Response.success(list)
//    }
//
//    override suspend fun getLine(): Response<Line> {
//        TODO("Not yet implemented")
//    }
//}
