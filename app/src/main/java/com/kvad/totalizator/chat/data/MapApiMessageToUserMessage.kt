package com.kvad.totalizator.chat.data

import com.kvad.totalizator.chat.model.UserMessage
import com.kvad.totalizator.chat.model.UserMessageApiModel
import com.kvad.totalizator.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import javax.inject.Inject

class MapApiMessageToUserMessage @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(apiMessageList: List<UserMessageApiModel>): List<UserMessage> =
        withContext(dispatcher) {
            apiMessageList.map {
                UserMessage(
                    id = it.id,
                    text = it.text,
                    image = it.image,
                    userId = it.userId,
                    userName = it.userName,
                    time = parseZonedDateTime(it.time)
                )
            }
        }


    private fun parseZonedDateTime(time: String): ZonedDateTime {
        return ZonedDateTime.parse(time)
    }
}
