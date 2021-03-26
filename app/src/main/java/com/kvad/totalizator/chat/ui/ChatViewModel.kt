package com.kvad.totalizator.chat.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.chat.data.ChatRepository
import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.di.IoDispatcher
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.mapSuccess
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

typealias EventState = State<List<UserMessageUi>, ErrorState>

class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository,
    private val mapUserMessageUi: MapMessagesToUi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _chatLiveData = MutableLiveData<EventState>()
    val chatLiveData = _chatLiveData

    private var currentUserId: String? = null

    init {
        _chatLiveData.value = State.Loading
        viewModelScope.launch {
//            launch {
//                getAccount()
//            }.join()
            getAccount()
            Log.d("ErrorFlow", currentUserId!!)
            updateChat()
        }
    }

    fun sendMessage(text: String) {
        viewModelScope.launch {
            chatRepository.sendMessage(text)
        }
    }

    private suspend fun getAccount() {
        val account = userRepository.accountInfo()
        if (account.isSuccess()) {
            currentUserId = account.asSuccess().value.id
            return
        }
        currentUserId = ""
    }

    private suspend fun updateChat() {
        chatRepository.message
            .map {
                it.mapSuccess { msg ->
                    mapUserMessageUi(currentUserId, msg)
                }
            }
            .collect {
                it.doOnResult(
                    onSuccess = ::doOnSuccess,
                    onError = ::doOnError
                )
            }
    }

    private fun doOnError(error: ApiResultWrapper.Error) {
        Log.d("ERROR_TAG", error.msg)
        _chatLiveData.value = State.Error(ErrorState.LOADING_ERROR)
    }

    private fun doOnSuccess(messageApiModelList: List<UserMessageUi>) {
        _chatLiveData.value = State.Content(messageApiModelList)
    }
}
