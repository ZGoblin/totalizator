package com.kvad.totalizator.chat.data

import com.kvad.totalizator.chat.model.SendMsg
import com.kvad.totalizator.chat.model.UserMessage
import com.kvad.totalizator.tools.REQUEST_DELAY
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.mapSuccess
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

typealias MassageWrapper = ApiResultWrapper<List<UserMessage>>

class ChatRepository @Inject constructor(
    private val mapApiMessageToUserMessage: MapApiMessageToUserMessage,
    private val chatService: ChatService
) {


    private val _message: MutableSharedFlow<MassageWrapper> = MutableSharedFlow(replay = 1)
    val message: SharedFlow<MassageWrapper> = _message

    init {
        GlobalScope.launch {
            flow {
                while (true) {
                    val messageList = safeApiCall(chatService::getMessage).mapSuccess {
                        mapApiMessageToUserMessage(it)
                    }
                    emit(messageList)
                    delay(REQUEST_DELAY)
                }
            }.collect {
                _message.emit(it)
            }
        }
    }

    suspend fun sendMessage(text: String) = withContext(Dispatchers.IO) {
        safeApiCall {
            chatService.sendMessage(SendMsg(text))
        }
    }
}
