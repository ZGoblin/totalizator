package com.kvad.totalizator.header

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.models.Wallet
import com.kvad.totalizator.tools.REQUEST_DELAY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class HeaderViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _walletLiveData = MutableLiveData<Wallet>()
    val walletLiveData = _walletLiveData

    fun getWallet() {
        viewModelScope.launch {
            while (true) {
                delay(REQUEST_DELAY)
                updateWallet()
            }
        }
    }

    private suspend fun updateWallet() {
        _walletLiveData.postValue(userRepository.getWallet())
    }
}
