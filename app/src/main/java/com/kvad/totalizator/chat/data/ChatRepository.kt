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
                        UserMessageApiModel(1, "hhlhdfg", "some text", "https://w7.pngwing.com/pngs/891/105/png-transparent-computer-icons-user-others-miscellaneous-face-service.png", "17:56"),
                        UserMessageApiModel(2, "hhlhdfg", "some text", "https://w7.pngwing.com/pngs/891/105/png-transparent-computer-icons-user-others-miscellaneous-face-service.png","17:56"),
                        UserMessageApiModel(3, "hhlhdfg", "some text", "https://w7.pngwing.com/pngs/891/105/png-transparent-computer-icons-user-others-miscellaneous-face-service.png","17:56"),
                        UserMessageApiModel(4, "hhlhdfg", "some text", "https://w7.pngwing.com/pngs/891/105/png-transparent-computer-icons-user-others-miscellaneous-face-service.png","17:56"),
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
