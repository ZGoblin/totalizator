package com.kvad.totalizator.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.models.Login
import com.kvad.totalizator.login.domain.LoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginStateViewModel = MutableLiveData<LoginState>()
    val loginStateViewModel = _loginStateViewModel

    fun login(login: Login) {
        viewModelScope.launch {
            _loginStateViewModel.postValue(loginUseCase.login(login))
        }
    }
}
