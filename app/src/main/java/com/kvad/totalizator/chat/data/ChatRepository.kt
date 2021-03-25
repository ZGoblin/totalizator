package com.kvad.totalizator.chat.data

import com.kvad.totalizator.chat.model.UserMessage
import com.kvad.totalizator.chat.model.UserMessageApiModel
import com.kvad.totalizator.tools.REQUEST_DELAY
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.mapSuccess
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val mapApiMessageToUserMessage: MapApiMessageToUserMessage
) {

    @Suppress("MaxLineLength", "MagicNumber")
    suspend fun getMessage() = flow<ApiResultWrapper<List<UserMessage>>> {

        while (true) {
            val msgLine = safeApiCall {
                Response.success(
                    listOf(
                        UserMessageApiModel("userId", "sd","userName" ,"some text", "https://avatars.dicebear.com/api/human/rodion.png", "2021-03-24T20:39:51.742Z"),
                        UserMessageApiModel("userId", "hhlhsdfdfg","userName" ,"some text", "https://avatars.dicebear.com/api/human/oleg.png","2021-03-24T20:39:51.742Z"),
                        UserMessageApiModel("userId", "hhlhdfg","userName" ,"some text", "https://avatars.dicebear.com/api/human/oleh.png","2021-03-24T20:39:51.742Z"),
                        UserMessageApiModel("userId", "hhlhddfg","userName" ,"some text", "https://avatars.dicebear.com/api/human/olexiy.png","2021-03-24T20:39:51.742Z"),
                    )
                )
            }.mapSuccess { mapApiMessageToUserMessage(it) }
            emit(msgLine)
            delay(REQUEST_DELAY)
        }

    }


    suspend fun getUserInfo(){
        return
    }
}
