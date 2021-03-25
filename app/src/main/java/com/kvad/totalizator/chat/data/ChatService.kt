package com.kvad.totalizator.chat.data

import com.kvad.totalizator.chat.model.UserMessageApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ChatService {

    @GET("/api/Chat")
    suspend fun getMessage() : Response<UserMessageApiResponse>

}
