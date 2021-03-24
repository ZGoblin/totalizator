package com.kvad.totalizator.chat.data

import com.kvad.totalizator.chat.model.UserMessage
import com.kvad.totalizator.tools.REQUEST_DELAY
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class ChatRepository @Inject constructor(

) {

    @Suppress("MaxLineLength", "MagicNumber")
    suspend fun getMessage() = flow<ApiResultWrapper<List<UserMessage>>> {

        while (true) {
            val msgLine = safeApiCall {
                Response.success(
                    listOf(
                        UserMessage(1, "hhlhdfg", "some text", "https://w7.pngwing.com/pngs/891/105/png-transparent-computer-icons-user-others-miscellaneous-face-service.png", "17:56"),
                        UserMessage(2, "hhlhdfg", "some text", "https://w7.pngwing.com/pngs/891/105/png-transparent-computer-icons-user-others-miscellaneous-face-service.png","17:56"),
                        UserMessage(3, "hhlhdfg", "some text", "https://w7.pngwing.com/pngs/891/105/png-transparent-computer-icons-user-others-miscellaneous-face-service.png","17:56"),
                        UserMessage(4, "hhlhdfg", "some text", "https://w7.pngwing.com/pngs/891/105/png-transparent-computer-icons-user-others-miscellaneous-face-service.png","17:56"),
                    )
                )
            }
            emit(msgLine)
            delay(REQUEST_DELAY)
        }

    }


    suspend fun getUserInfo(){
        return
    }
}
