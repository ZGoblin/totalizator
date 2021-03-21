package com.kvad.totalizator.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.models.LoginRequest
import com.kvad.totalizator.login.domain.LoginUseCase
import com.kvad.totalizator.tools.sharedPrefTools.SharedPref
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginStateViewModel = MutableLiveData<LoginState>()
    val loginStateViewModel: LiveData<LoginState> = _loginStateViewModel

    fun login(loginRequest: LoginRequest) {
        Log.d("safeApiCall", loginRequest.toString())
        viewModelScope.launch {
            _loginStateViewModel.value = loginUseCase.login(loginRequest)
        }
    }
}
