package com.kvad.totalizator.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.requestmodels.AccountInfo
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.sharedPrefTools.SharedPref
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias AccountState = State<AccountInfo, ErrorState>

class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPref: SharedPref
): ViewModel() {

    private val _accountLiveData = MutableLiveData<AccountState>()
    val accountLiveData: LiveData<AccountState> = _accountLiveData

    fun updateAccountInfo() {
        viewModelScope.launch {
            userRepository.accountInfo().doOnResult(
                onSuccess = ::doOnSuccessAccount,
                onError = ::doOnErrorAccount
            )
        }
    }

    fun logout() {
        sharedPref.token = ""
    }

    private fun doOnSuccessAccount(accountInfo: AccountInfo) {
        _accountLiveData.value = State.Content(accountInfo)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun doOnErrorAccount(error: ApiResultWrapper.Error) {
        _accountLiveData.value = State.Error(ErrorState.LOADING_ERROR)
    }
}
