package com.kvad.totalizator.login.domain

import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.models.Login
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
    suspend fun login(login: Login) : LoginState {
        return withContext(Dispatchers.Default) {

            val state = checkLoginComponent(login)
            if (state == LoginState.WITHOUT_ERROR) {
                when (userRepository.login(login)) {
                    is ResultWrapper.Success -> return@withContext LoginState.WITHOUT_ERROR
                    else -> return@withContext LoginState.NETWORK_ERROR
                }
            }

            state
        }
    }

    private suspend fun checkLoginComponent(login: Login) : LoginState {
        return withContext(Dispatchers.Default) {
            when {
                login.login.length < LOGIN_MIN_LENGTH -> LoginState.LOGIN_LENGTH_ERROR
                login.password.length < PASSWORD_MIN_LENGTH -> LoginState.PASSWORD_LENGTH_ERROR
                else -> LoginState.WITHOUT_ERROR
            }
        }
    }
}