package com.kvad.totalizator.login.domain

import android.util.Log
import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.models.LoginRequest
import com.kvad.totalizator.data.models.Token
import com.kvad.totalizator.login.LoginState
import com.kvad.totalizator.tools.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    private lateinit var state: LoginState

    suspend fun login(loginRequest: LoginRequest) = withContext(Dispatchers.Default) {

        state = verifyLoginComponent(loginRequest)

        if (state == LoginState.WITHOUT_ERROR) {
            userRepository.login(loginRequest).doOnResult(
                onSuccess = ::doOnSuccess,
                onNetworkError = ::doOnNetworkError
            )
        }

        return@withContext state
    }


    private fun doOnSuccess(token: Token) {
        userRepository.updateToken(token)
        state = LoginState.WITHOUT_ERROR
    }

    private fun doOnNetworkError(error: ResultWrapper.Error) {
        Log.d("ERROR_TAG", error.msg)
        state = LoginState.NETWORK_ERROR
    }

    private suspend fun verifyLoginComponent(loginRequest: LoginRequest) =
        withContext(Dispatchers.Default) {
            when {
                loginRequest.login.length < LOGIN_MIN_LENGTH -> LoginState.LOGIN_LENGTH_ERROR
                loginRequest.password.length < PASSWORD_MIN_LENGTH -> LoginState.PASSWORD_LENGTH_ERROR
                else -> LoginState.WITHOUT_ERROR
            }
        }
}
