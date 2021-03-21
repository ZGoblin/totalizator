package com.kvad.totalizator.registration.domain

import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.models.RegisterRequest
import com.kvad.totalizator.registration.RegisterState
import com.kvad.totalizator.registration.models.RawRegisterRequest
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.LOGIN_MIN_LENGTH
import com.kvad.totalizator.tools.PASSWORD_MIN_LENGTH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun register(rawRegisterRequest: RawRegisterRequest) = withContext(Dispatchers.Default) {

        val state = verifyRegisterComponent(rawRegisterRequest)
        if (state == RegisterState.WITHOUT_ERROR) {
            when (val result = userRepository.register(toRegisterRequest(rawRegisterRequest))) {
                is ResultWrapper.Success -> {
                    userRepository.updateToken(result.value)
                    return@withContext RegisterState.WITHOUT_ERROR
                }
                else -> return@withContext RegisterState.NETWORK_ERROR
            }
        }

        state
    }

    private suspend fun verifyRegisterComponent(rawRegisterRequest: RawRegisterRequest) = withContext(Dispatchers.Default) {
        when {
            rawRegisterRequest.email.length < LOGIN_MIN_LENGTH -> RegisterState.LOGIN_LENGTH_ERROR
            rawRegisterRequest.password.length < PASSWORD_MIN_LENGTH -> RegisterState.PASSWORD_LENGTH_ERROR
            !isAdult(rawRegisterRequest) -> RegisterState.BIRTHDAY_ERROR
            else -> RegisterState.WITHOUT_ERROR
        }
    }

    private suspend fun isAdult(rawRegisterRequest: RawRegisterRequest) = withContext(Dispatchers.Default) {

        val calendarAdult = Calendar.getInstance()
        calendarAdult.add(Calendar.YEAR, -18)
        val calendarBirthday = Calendar.getInstance()
        calendarBirthday.set(
            rawRegisterRequest.year,
            rawRegisterRequest.month,
            rawRegisterRequest.day
        )

        calendarAdult >= calendarBirthday
    }

    private suspend fun toRegisterRequest(rawRegisterRequest: RawRegisterRequest) = withContext(Dispatchers.Default) {
        RegisterRequest(
            email = rawRegisterRequest.email,
            password = rawRegisterRequest.password,
            dob = "${rawRegisterRequest.year}-${
                if (rawRegisterRequest.month > 9) rawRegisterRequest.month
                else "0${rawRegisterRequest.month}"
            }-${rawRegisterRequest.day}"
        )
    }
}
