package com.kvad.totalizator.chat.data

import com.kvad.totalizator.chat.model.SendMsg
import com.kvad.totalizator.tools.REQUEST_DELAY
import com.kvad.totalizator.tools.safeapicall.mapSuccess
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val mapApiMessageToUserMessage: MapApiMessageToUserMessage,
    private val chatService: ChatService
) {

    suspend fun getMessageFromApi() = flow {

        while (true) {
            val messageList = safeApiCall(chatService::getMessage).mapSuccess {
                mapApiMessageToUserMessage(it)
            }
            emit(messageList)
            delay(REQUEST_DELAY)
        }

    }

    suspend fun sendMessage(text: String) = withContext(Dispatchers.IO) {
        chatService.sendMessage(SendMsg(text))
    }

}
