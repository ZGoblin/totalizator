package com.kvad.totalizator.chat.ui

import androidx.lifecycle.ViewModel
import com.kvad.totalizator.chat.data.ChatRepository
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    val chatRepository: ChatRepository
): ViewModel() {


}
