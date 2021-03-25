package com.kvad.totalizator.login.domain

import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.requestmodels.LoginRequest
import com.kvad.totalizator.data.requestmodels.Token
import com.kvad.totalizator.di.DefaultDispatcher
import com.kvad.totalizator.login.LoginState
import com.kvad.totalizator.tools.LOGIN_MIN_LENGTH
import com.kvad.totalizator.tools.PASSWORD_MIN_LENGTH
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {

    private lateinit var state: LoginState

    suspend fun login(loginRequest: LoginRequest) = withContext(dispatcher) {

        state = verifyLoginComponent(loginRequest)

        if (state == LoginState.WITHOUT_ERROR) {
            userRepository.login(loginRequest).doOnResult(
                onSuccess = ::doOnSuccess,
                onError = ::doOnError
            )
        }

        return@withContext state
    }


    private fun doOnSuccess(token: Token) {
        userRepository.updateToken(token)
        state = LoginState.WITHOUT_ERROR
    }

    @Suppress("UNUSED_PARAMETER")
    private fun doOnError(error: ApiResultWrapper.Error) {
        state = LoginState.NETWORK_ERROR
    }

    private suspend fun verifyLoginComponent(loginRequest: LoginRequest) =
        withContext(dispatcher) {
            when {
                loginRequest.login.length < LOGIN_MIN_LENGTH -> LoginState.LOGIN_LENGTH_ERROR
                loginRequest.password.length < PASSWORD_MIN_LENGTH -> LoginState.PASSWORD_LENGTH_ERROR
                else -> LoginState.WITHOUT_ERROR
            }
        }
}
