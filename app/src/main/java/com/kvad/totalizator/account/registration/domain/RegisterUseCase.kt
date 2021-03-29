package com.kvad.totalizator.account.registration.domain

import com.kvad.totalizator.account.data.UserRepository
import com.kvad.totalizator.account.data.model.RegisterRequest
import com.kvad.totalizator.di.DefaultDispatcher
import com.kvad.totalizator.account.registration.RegisterState
import com.kvad.totalizator.account.registration.models.RawRegisterRequest
import com.kvad.totalizator.tools.USERNAME_MIN_LENGTH
import com.kvad.totalizator.tools.PASSWORD_MIN_LENGTH
import com.kvad.totalizator.tools.ADULT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.regex.Pattern
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {

    private lateinit var state: RegisterState

    suspend fun register(rawRegisterRequest: RawRegisterRequest) = withContext(dispatcher) {

        state = verifyRegisterComponent(rawRegisterRequest)
        if (state == RegisterState.WITHOUT_ERROR) {
            userRepository.register(toRegisterRequest(rawRegisterRequest)).doOnResult(
                onSuccess = { state = RegisterState.WITHOUT_ERROR },
                onError = { state = RegisterState.NETWORK_ERROR }
            )
        }

        state
    }

    private suspend fun verifyRegisterComponent(rawRegisterRequest: RawRegisterRequest) =
        withContext(dispatcher) {
            when {
                !isValidEmail(rawRegisterRequest.email) -> RegisterState.EMAIL_ERROR
                rawRegisterRequest.username.length < USERNAME_MIN_LENGTH -> RegisterState.USERNAME_ERROR
                rawRegisterRequest.password.length < PASSWORD_MIN_LENGTH -> RegisterState.PASSWORD_LENGTH_ERROR
                !isAdult(rawRegisterRequest) -> RegisterState.BIRTHDAY_ERROR
                else -> RegisterState.WITHOUT_ERROR
            }
        }

    private suspend fun isValidEmail(email: String) = withContext(dispatcher) {
        if (email.isNotEmpty()) {
            val pattern = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" +
                        ")+"
            )
            return@withContext pattern.matcher(email).matches()
        }
        return@withContext false
    }

    private suspend fun isAdult(rawRegisterRequest: RawRegisterRequest) =
        withContext(dispatcher) {

            val calendarAdult = Calendar.getInstance()
            calendarAdult.add(Calendar.YEAR, (ADULT - ADULT * 2))
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
        ZonedDateTime.of(
            rawRegisterRequest.year,
            rawRegisterRequest.month,
            rawRegisterRequest.day,
            0, 0, 0, 0,
            ZoneId.systemDefault()
        )
            .toLocalDateTime()
            .toString()
    }
}
