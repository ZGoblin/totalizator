package com.kvad.totalizator.header

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.requestmodels.AccountInfo
import com.kvad.totalizator.data.requestmodels.Wallet
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias HeaderState = State<Wallet, ErrorState>
typealias AccountState = State<AccountInfo, ErrorState>

class HeaderViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _walletLiveData = MutableLiveData<HeaderState>()
    val walletLiveData: LiveData<HeaderState> = _walletLiveData

    private val _accountLiveData = MutableLiveData<AccountState>()
    val accountLiveData: LiveData<AccountState> = _accountLiveData

    var accountAvatar: String? = null

    init {
        _walletLiveData.value = State.Loading
        viewModelScope.launch {
            userRepository.wallet.collect {
                updateWallet(it)
            }
        }
    }

    private fun updateWallet(apiResultWrapper: ApiResultWrapper<Wallet>) {
        apiResultWrapper.doOnResult(
            onSuccess = ::doOnSuccess,
            onError = ::doOnLoginError
        )
    }

    private fun doOnSuccess(wallet: Wallet) {
        updateAccountInfo()
        _walletLiveData.value = State.Content(wallet)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun doOnLoginError(error: ApiResultWrapper.Error) {
        _walletLiveData.value = State.Error(ErrorState.LOGIN_ERROR)
    }

    private fun updateAccountInfo() {
        if (_walletLiveData.value is State.Error || _walletLiveData.value is State.Loading) {
            viewModelScope.launch {
                userRepository.accountInfo().doOnResult(
                    onSuccess = ::doOnSuccessAccount,
                    onError = ::doOnErrorAccount
                )
            }
        }
    }

    private fun doOnSuccessAccount(accountInfo: AccountInfo) {
        _accountLiveData.value = State.Content(accountInfo)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun doOnErrorAccount(error: ApiResultWrapper.Error) {
        _accountLiveData.value = State.Error(ErrorState.LOADING_ERROR)
    }
}
