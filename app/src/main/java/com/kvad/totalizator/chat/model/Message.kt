package com.kvad.totalizator.chat.model

import java.time.ZonedDateTime

data class UserMessageApiModel(
    val id: String,
    val userId: String,
    val userName: String,
    val text: String,
    val image: String,
    val time: String
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
    val text: String
)

data class UserInfo(
    val userId: String,
    val photoLink: String,
    val email: String
)
