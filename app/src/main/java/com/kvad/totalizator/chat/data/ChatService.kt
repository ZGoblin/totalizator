package com.kvad.totalizator.chat.data

import com.kvad.totalizator.chat.model.SendMsg
import com.kvad.totalizator.chat.model.UserMessageApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatService {

    @GET("/api/Chat")
    suspend fun getMessage() : Response<UserMessageApiResponse>

    @POST("/api/Chat")
    suspend fun sendMessage(
        @Body message: SendMsg
    ) : Response<Unit>
}
