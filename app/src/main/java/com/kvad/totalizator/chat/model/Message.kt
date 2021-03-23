package com.kvad.totalizator.chat.model

data class Message(
    val id: Int,
    val text: String,
    val image: String,
    val time: String
)

val serverResponse: List<Message> = emptyList()

data class SendMsg(
    val text: String
)


data class userAcc(
    val userId: String,
    val photoLink: String,
    val email: String
)
