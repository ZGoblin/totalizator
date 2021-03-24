package com.kvad.totalizator.chat.ui

import com.kvad.totalizator.chat.UserMessageUi
import com.kvad.totalizator.chat.model.UserMessage
import com.kvad.totalizator.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import javax.inject.Inject

class MapMessagesToUi @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(currentUserId: String?, messagesList: List<UserMessage>) =
        withContext(dispatcher) {
            messagesList.map {
                UserMessageUi(
                    id = it.id,
                    userName = it.userName,
                    text = it.text,
                    image = it.image,
                    time = getStringTime(it.time),
                    isCurrentUserMessage = (it.userId == currentUserId)
                )
            }
        }

    private fun getStringTime(time: ZonedDateTime) = "${time.hour}:${time.minute}"

}
