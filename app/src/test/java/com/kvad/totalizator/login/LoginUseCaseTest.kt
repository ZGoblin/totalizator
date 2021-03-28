package com.kvad.totalizator.login

import com.kvad.totalizator.accaunt.data.UserRepository
import com.kvad.totalizator.accaunt.data.model.LoginRequest
import com.kvad.totalizator.accaunt.login.LoginState
import com.kvad.totalizator.accaunt.login.domain.LoginUseCase
import com.kvad.totalizator.shared.Token
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class LoginUseCaseTest {
    private val userRepository = mockk<UserRepository>()
    private val loginUseCase = LoginUseCase(userRepository, TestCoroutineDispatcher())

    @Test
    fun `if email not include @ return EMAIL_DOG_NOT_INCLUDE`() {
        val testingRequest = LoginRequest(
            login = "login",
            password = "password",
        )

        runBlocking {
            loginUseCase.login(testingRequest) shouldBe LoginState.EMAIL_DOG_NOT_INCLUDE
        }
    }

    @Test
    fun `if email length less then 3 return EMAIL_LENGTH_ERROR`() {
        val testingRequest = LoginRequest(
            login = "lo",
            password = "password",
        )

        runBlocking {
            loginUseCase.login(testingRequest) shouldBe LoginState.EMAIL_LENGTH_ERROR
        }
    }

    @Test
    fun `if user doesn't exist return NETWORK_ERROR`() {
        coEvery { userRepository.login(any()) } returns ApiResultWrapper.Error.LoginError("")

        val testingRequest = LoginRequest(
            login = "@login",
            password = "password",
        )

        runBlocking {
            loginUseCase.login(testingRequest) shouldBe LoginState.NETWORK_ERROR
        }
    }

    @Test
    fun `if password length less then 6 return PASSWORD_LENGTH_ERROR`() {
        val testingRequest = LoginRequest(
            login = "@login",
            password = "pas",
        )

        runBlocking {
            loginUseCase.login(testingRequest) shouldBe LoginState.PASSWORD_LENGTH_ERROR
        }
    }

    @Test
    fun `if all okay return WITHOUT_ERROR`() {
        coEvery { userRepository.login(any()) } returns ApiResultWrapper.Success(Token(""))

        val testingRequest = LoginRequest(
            login = "@login",
            password = "password",
        )

        runBlocking {
            loginUseCase.login(testingRequest) shouldBe LoginState.WITHOUT_ERROR
        }
    }
}