package com.kvad.totalizator.chat.data

import com.kvad.totalizator.chat.model.Message
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import retrofit2.Response
import javax.inject.Inject

class ChatRepository @Inject constructor(

) {

    suspend fun getMessage(): ApiResultWrapper<List<Message>> {
        return safeApiCall {
            Response.success(
                listOf(
                    Message(1, "hhlhdfg", "some text", "https://w7.pngwing.com/pngs/891/105/png-transparent-computer-icons-user-others-miscellaneous-face-service.png"),
                    Message(2, "hhlhdfg", "some text", "https://w7.pngwing.com/pngs/891/105/png-transparent-computer-icons-user-others-miscellaneous-face-service.png"),
                    Message(3, "hhlhdfg", "some text", "https://w7.pngwing.com/pngs/891/105/png-transparent-computer-icons-user-others-miscellaneous-face-service.png"),
                    Message(4, "hhlhdfg", "some text", "https://w7.pngwing.com/pngs/891/105/png-transparent-computer-icons-user-others-miscellaneous-face-service.png"),
                )
            )
        }
    }

}