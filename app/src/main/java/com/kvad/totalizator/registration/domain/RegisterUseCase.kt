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

    suspend fun register(rawRegisterRequest: RawRegisterRequest): RegisterState {
        return withContext(Dispatchers.Default) {

            val state = verifyRegisterComponent(rawRegisterRequest)
            if (state == RegisterState.WITHOUT_ERROR) {
                when (userRepository.register(toRegisterRequest(rawRegisterRequest))) {
                    is ResultWrapper.Success -> return@withContext RegisterState.WITHOUT_ERROR
                    else -> return@withContext RegisterState.NETWORK_ERROR
                }
            }

            state
        }
    }

    private suspend fun verifyRegisterComponent(rawRegisterRequest: RawRegisterRequest): RegisterState {
        return withContext(Dispatchers.Default) {
            when {
                rawRegisterRequest.email.length < LOGIN_MIN_LENGTH -> RegisterState.LOGIN_LENGTH_ERROR
                rawRegisterRequest.password.length < PASSWORD_MIN_LENGTH -> RegisterState.PASSWORD_LENGTH_ERROR
                !isAdult(rawRegisterRequest) -> RegisterState.BIRTHDAY_ERROR
                else -> RegisterState.WITHOUT_ERROR
            }
        }
    }

    private suspend fun isAdult(rawRegisterRequest: RawRegisterRequest): Boolean {
        return withContext(Dispatchers.Default) {

            val calendar = Calendar.getInstance()
            val diffYear = calendar.get(Calendar.YEAR) - rawRegisterRequest.year
            when {
                diffYear > 18 -> true
                diffYear < 18 -> false
                else -> {
                    when {
                        calendar.get(Calendar.MONTH) > rawRegisterRequest.month -> true
                        calendar.get(Calendar.MONTH) < rawRegisterRequest.month -> false
                        else -> {
                            calendar.get(Calendar.DAY_OF_MONTH) >= rawRegisterRequest.day
                        }
                    }
                }
            }
        }
    }

    private suspend fun toRegisterRequest(rawRegisterRequest: RawRegisterRequest): RegisterRequest {
        return withContext(Dispatchers.Default) {
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
}
