package com.kvad.totalizator.header

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.models.Wallet
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.REQUEST_DELAY
import com.kvad.totalizator.tools.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias HeaderState = State<Wallet, ErrorState>

class HeaderViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _headerLiveData = MutableLiveData<HeaderState>()
    val headerLiveData: LiveData<HeaderState> = _headerLiveData

    fun getWallet(){
        viewModelScope.launch {
            while (true) {
                delay(REQUEST_DELAY)
                updateWallet()
            }
        }
    }

    private suspend fun updateWallet() {
        when (val result = userRepository.wallet()) {
            is ResultWrapper.Success -> _headerLiveData.value = State.Content(result.value)
            is ResultWrapper.DataLoadingError -> _headerLiveData.value = State.Error(ErrorState.LOADING_ERROR)
            is ResultWrapper.LoginError -> _headerLiveData.value = State.Error(ErrorState.LOGIN_ERROR)
        }
    }
}
