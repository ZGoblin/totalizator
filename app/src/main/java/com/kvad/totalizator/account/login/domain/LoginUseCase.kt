package com.kvad.totalizator.account.login.domain

import com.kvad.totalizator.account.data.UserRepository
import com.kvad.totalizator.account.data.model.LoginRequest
import com.kvad.totalizator.di.DefaultDispatcher
import com.kvad.totalizator.account.login.LoginState
import com.kvad.totalizator.tools.PASSWORD_MIN_LENGTH
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.regex.Pattern
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
                onSuccess = { state = LoginState.WITHOUT_ERROR },
                onError = {  state = LoginState.NETWORK_ERROR }
            )
        }

        return@withContext state
    }

    private suspend fun verifyLoginComponent(loginRequest: LoginRequest) =
        withContext(dispatcher) {
            when {
                !isValidLogin(loginRequest.login) -> LoginState.EMAIL_VALIDATION_ERROR
                loginRequest.password.length < PASSWORD_MIN_LENGTH -> LoginState.PASSWORD_LENGTH_ERROR
                else -> LoginState.WITHOUT_ERROR
            }
        }

    private suspend fun isValidLogin(login: String) = withContext(dispatcher) {
        if (login.isNotEmpty()) {
            val pattern = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" +
                        ")+"
            )
            return@withContext pattern.matcher(login).matches()
        }
        return@withContext false
    }
}
