package com.kvad.totalizator.login.domain

import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.models.LoginRequest
import com.kvad.totalizator.login.LoginState
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.LOGIN_MIN_LENGTH
import com.kvad.totalizator.tools.PASSWORD_MIN_LENGTH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun login(loginRequest: LoginRequest) = withContext(Dispatchers.Default) {

        val state = verifyLoginComponent(loginRequest)
        if (state == LoginState.WITHOUT_ERROR) {
            when (val result = userRepository.login(loginRequest)) {
                is ResultWrapper.Success -> {
                    userRepository.updateToken(result.value)
                    return@withContext LoginState.WITHOUT_ERROR
                }
                else -> return@withContext LoginState.NETWORK_ERROR
            }
        }

        state
    }

    private suspend fun verifyLoginComponent(loginRequest: LoginRequest) = withContext(Dispatchers.Default) {
        when {
            loginRequest.login.length < LOGIN_MIN_LENGTH -> LoginState.LOGIN_LENGTH_ERROR
            loginRequest.password.length < PASSWORD_MIN_LENGTH -> LoginState.PASSWORD_LENGTH_ERROR
            else -> LoginState.WITHOUT_ERROR
        }
    }
}
