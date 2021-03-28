package com.kvad.totalizator.transaction.domain

import com.kvad.totalizator.account.data.MapperTransactionToTransactionRequest
import com.kvad.totalizator.account.data.UserRepository
import com.kvad.totalizator.account.data.model.TransactionRequest
import com.kvad.totalizator.account.data.model.Wallet
import com.kvad.totalizator.account.transaction.domain.TransactionType
import com.kvad.totalizator.account.transaction.domain.WithdrawUseCase
import com.kvad.totalizator.account.transaction.model.TransactionModel
import com.kvad.totalizator.account.transaction.withdraw.WithdrawRepository
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class WithdrawUseCaseTest {

    @Test
    fun `if user do transaction withdraw return unit`() {
        val mockMapper = mockk<MapperTransactionToTransactionRequest>(relaxed = true)
        val mockWithdrawRepository = mockk<WithdrawRepository> {
            coEvery { doTransaction(any()) } returns ApiResultWrapper.Success(value = Unit)
        }
        val mockUserRepository = mockk<UserRepository> {
            coEvery { getLastWallet() } returns Wallet(amount = 100.0)
        }
        val withdrawUseCase =
            WithdrawUseCase(
                mockUserRepository,
                mockWithdrawRepository,
                mockMapper,
                TestCoroutineDispatcher()
            )
        val testingModel = TransactionModel(amount = 10.0, type = TransactionType.WITHDRAW)
        runBlocking {
            withdrawUseCase.withdraw(testingModel) shouldBe ApiResultWrapper.Success(value = Unit)
        }

    }

    @Test
    fun `if user do transaction withdraw more money than have in wallet`() {
        val mockMapper = mockk<MapperTransactionToTransactionRequest> {
            every { map(any()) } returns TransactionRequest(amount = 100.0, type = "withdraw")
        }
        val mockUserRepository = mockk<UserRepository> {
            coEvery { getLastWallet() } returns Wallet(amount = 20.0)
        }

        val mockWithdrawRepository = mockk<WithdrawRepository>(relaxUnitFun = true)
        val betModel = mockk<TransactionModel>()
        val withdrawUseCase =
            WithdrawUseCase(
                mockUserRepository, mockWithdrawRepository, mockMapper,
                TestCoroutineDispatcher()
            )
        runBlocking {
            withdrawUseCase.withdraw(betModel) shouldBe ApiResultWrapper.Error.NoMoneyError(message = "Money Error")
        }
    }
}