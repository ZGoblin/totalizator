package com.kvad.totalizator.chat.model

data class UserMessage(
    val id: Int,
    val userId: String,
    val text: String,
    val image: String,
    val time: String
)

data class SendMsg(
    val text: String
)

data class UserInfo(
    val userId: String,
    val photoLink: String,
    val email: String
)
