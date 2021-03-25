package com.kvad.totalizator.chat.ui

data class UserMessageUi(
    val id: String,
    val userName: String,
    val text: String,
    val image: String,
    val time: String,
    val isCurrentUserMessage: Boolean
)
