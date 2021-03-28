package com.kvad.totalizator.account.registration.domain

import com.kvad.totalizator.account.data.UserRepository
import com.kvad.totalizator.account.data.model.RegisterRequest
import com.kvad.totalizator.shared.Token
import com.kvad.totalizator.di.DefaultDispatcher
import com.kvad.totalizator.account.registration.RegisterState
import com.kvad.totalizator.account.registration.models.RawRegisterRequest
import com.kvad.totalizator.tools.ADULT
import com.kvad.totalizator.tools.LOGIN_MIN_LENGTH
import com.kvad.totalizator.tools.PASSWORD_MIN_LENGTH
import com.kvad.totalizator.tools.USERNAME_MIN_LENGTH
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.StringBuilder
import java.util.*
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {
    companion object {
        private const val MONTH_BEFORE_ADD_ZERO = 9
        private const val DATE_SEPARATOR = "-"
    }

    private lateinit var state: RegisterState

    suspend fun register(rawRegisterRequest: RawRegisterRequest) = withContext(dispatcher) {

        val state = verifyRegisterComponent(rawRegisterRequest)
        if (state == RegisterState.WITHOUT_ERROR) {
            userRepository.register(toRegisterRequest(rawRegisterRequest)).doOnResult(
                onSuccess = ::doOnSuccess,
                onError = ::doOnError
            )
        }

        state
    }

    @Suppress("UNUSED_PARAMETER")
    private fun doOnSuccess(token: Token) {
        state = RegisterState.WITHOUT_ERROR
    }

    @Suppress("UNUSED_PARAMETER")
    private fun doOnError(error: ApiResultWrapper.Error) {
        state = RegisterState.NETWORK_ERROR
    }

    private suspend fun verifyRegisterComponent(rawRegisterRequest: RawRegisterRequest) =
        withContext(dispatcher) {
            when {
                rawRegisterRequest.email.length < LOGIN_MIN_LENGTH -> RegisterState.LOGIN_LENGTH_ERROR
                rawRegisterRequest.username.length < USERNAME_MIN_LENGTH -> RegisterState.USERNAME_ERROR
                rawRegisterRequest.password.length < PASSWORD_MIN_LENGTH -> RegisterState.PASSWORD_LENGTH_ERROR
                !isAdult(rawRegisterRequest) -> RegisterState.BIRTHDAY_ERROR
                else -> RegisterState.WITHOUT_ERROR
            }
        }

    private suspend fun isAdult(rawRegisterRequest: RawRegisterRequest) =
        withContext(dispatcher) {

            val calendarAdult = Calendar.getInstance()
            calendarAdult.add(Calendar.YEAR, ADULT.inv())
            val calendarBirthday = Calendar.getInstance()
            calendarBirthday.set(
                rawRegisterRequest.year,
                rawRegisterRequest.month,
                rawRegisterRequest.day
            )

            calendarAdult >= calendarBirthday
        }

    private suspend fun toRegisterRequest(rawRegisterRequest: RawRegisterRequest) = withContext(dispatcher) {
        RegisterRequest(
            username = rawRegisterRequest.username,
            email = rawRegisterRequest.email,
            password = rawRegisterRequest.password,
            dob = dateToString(rawRegisterRequest)
        )
    }

    private suspend fun dateToString(rawRegisterRequest: RawRegisterRequest) = withContext(dispatcher) {
        StringBuilder()
            .append(rawRegisterRequest.year)
            .append(DATE_SEPARATOR)
            .append(
                if (rawRegisterRequest.month > MONTH_BEFORE_ADD_ZERO)
                    rawRegisterRequest.month
                else
                    "0${rawRegisterRequest.month}"
            )
            .append(DATE_SEPARATOR)
            .append(rawRegisterRequest.day)
            .toString()
    }
}
