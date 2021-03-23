package com.kvad.totalizator.header

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.requestmodels.Wallet
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias HeaderState = State<Wallet, ErrorState>

class HeaderViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _headerLiveData = MutableLiveData<HeaderState>()
    val headerLiveData: LiveData<HeaderState> = _headerLiveData

    fun getWallet() {
        viewModelScope.launch {
            userRepository.wallet().collect {
                updateWallet(it)
            }
        }
    }

    private fun updateWallet(apiResultWrapper: ApiResultWrapper<Wallet>) {
        apiResultWrapper.doOnResult(
            onSuccess = ::doOnSuccess,
            onLoginError = ::doOnLoginError,
            onNetworkError = ::doOnNetworkError
        )
    }


    private fun doOnSuccess(wallet: Wallet) {
        _headerLiveData.value = State.Content(wallet)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun doOnLoginError(error: ApiResultWrapper.Error){
        _headerLiveData.value = State.Error(ErrorState.LOGIN_ERROR)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun doOnNetworkError(error: ApiResultWrapper.Error){
        _headerLiveData.value = State.Error(ErrorState.LOADING_ERROR)
    }
}
