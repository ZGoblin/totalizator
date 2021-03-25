package com.kvad.totalizator.chat.model

import com.google.gson.annotations.SerializedName
import java.time.ZonedDateTime

data class UserMessageApiResponse(
    @SerializedName("messages") val message: List<UserMessageApiModel>
)

data class UserMessageApiModel(
    @SerializedName("id") val id: String,
    @SerializedName("text") val text: String,
    @SerializedName("username") val userName: String,
    @SerializedName("account_Id") val userId: String,
    @SerializedName("avatarLink") val image: String,
    @SerializedName("time") val time: String
)

data class UserMessage(
    val id: String,
    val userId: String,
    val userName: String,
    val text: String,
    val image: String,
    val time: ZonedDateTime
)

data class SendMsg(
    @SerializedName("text") val text: String
)

data class UserInfo(
    val userId: String,
    val photoLink: String,
    val email: String
)
