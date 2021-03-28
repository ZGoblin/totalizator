package com.kvad.totalizator.registration

import com.kvad.totalizator.account.data.UserRepository
import com.kvad.totalizator.account.registration.RegisterState
import com.kvad.totalizator.account.registration.domain.RegisterUseCase
import com.kvad.totalizator.account.registration.models.RawRegisterRequest
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
internal class RegisterUseCaseTest() {
    private val userRepository = mockk<UserRepository>()
    private val registerUseCase = RegisterUseCase(userRepository, TestCoroutineDispatcher())

    @Test
    fun `if user not adult return BIRTHDAY_ERROR`() {
        val testingRequest = RawRegisterRequest(
            username = "username",
            email = "@email.com",
            password = "password",
            day = 24,
            month = 12,
            year = 2004
        )

        runBlocking {
            registerUseCase.register(testingRequest) shouldBe RegisterState.BIRTHDAY_ERROR
        }
    }

    @Test
    fun `if username length less then 3 return USERNAME_ERROR`() {
        val testingRequest = RawRegisterRequest(
            username = "us",
            email = "@email.com",
            password = "password",
            day = 24,
            month = 12,
            year = 1900
        )

        runBlocking {
            registerUseCase.register(testingRequest) shouldBe RegisterState.USERNAME_ERROR
        }
    }

    @Test
    fun `if email length less then 3 return EMAIL_LENGTH_ERROR`() {
        val testingRequest = RawRegisterRequest(
            username = "username",
            email = "@e",
            password = "password",
            day = 24,
            month = 12,
            year = 1900
        )

        runBlocking {
            registerUseCase.register(testingRequest) shouldBe RegisterState.EMAIL_LENGTH_ERROR
        }
    }

    @Test
    fun `if password length less then 6 return PASSWORD_LENGTH_ERROR`() {
        val testingRequest = RawRegisterRequest(
            username = "username",
            email = "@email.com",
            password = "pas",
            day = 24,
            month = 12,
            year = 1900
        )

        runBlocking {
            registerUseCase.register(testingRequest) shouldBe RegisterState.PASSWORD_LENGTH_ERROR
        }
    }

    @Test
    fun `if email not include @ return EMAIL_ERROR`() {
        val testingRequest = RawRegisterRequest(
            username = "username",
            email = "email.com",
            password = "password",
            day = 24,
            month = 12,
            year = 1900
        )

        runBlocking {
            registerUseCase.register(testingRequest) shouldBe RegisterState.EMAIL_ERROR
        }
    }

    @Test
    fun `if user exist return NETWORK_ERROR`() {
        coEvery { userRepository.register(any()) } returns ApiResultWrapper.Error.LoginError("")

        val testingRequest = RawRegisterRequest(
            username = "username",
            email = "@email.com",
            password = "password",
            day = 24,
            month = 12,
            year = 1900
        )

        runBlocking {
            registerUseCase.register(testingRequest) shouldBe RegisterState.NETWORK_ERROR
        }
    }

    @Test
    fun `if all okay return WITHOUT_ERROR`() {
        coEvery { userRepository.register(any()) } returns ApiResultWrapper.Success(Token(""))

        val testingRequest = RawRegisterRequest(
            username = "username",
            email = "@email.com",
            password = "password",
            day = 24,
            month = 12,
            year = 1900
        )

        runBlocking {
            registerUseCase.register(testingRequest) shouldBe RegisterState.WITHOUT_ERROR
        }
    }
}