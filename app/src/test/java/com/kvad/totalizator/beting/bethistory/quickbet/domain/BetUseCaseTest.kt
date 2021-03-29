package com.kvad.totalizator.beting.bethistory.quickbet.domain

import com.kvad.totalizator.account.data.UserRepository
import com.kvad.totalizator.account.data.model.Wallet
import com.kvad.totalizator.beting.quickbet.domain.BetUseCase
import com.kvad.totalizator.beting.quickbet.model.BetToServerModel
import com.kvad.totalizator.beting.data.BetRepository
import com.kvad.totalizator.beting.data.BetRequest
import com.kvad.totalizator.beting.data.MapperBetModelToBetRequest
import com.kvad.totalizator.shared.Bet
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
internal class BetUseCaseTest {


    @Test
    fun `if user create bet works correctly`() {
        val mockMapper = mockk<MapperBetModelToBetRequest>(relaxed = true)
        val mockUserRepository = mockk<UserRepository> {
            coEvery { getLastWallet() } returns Wallet(amount = 100.0)
        }

        val mockBetRepository = mockk<BetRepository> {
            coEvery { doBet(any()) } returns ApiResultWrapper.Success(value = Unit)
        }

        val betModel = BetToServerModel(eventId = "1", amount = 20.0, choice = Bet.DRAW)

        val betUseCase = BetUseCase(mockUserRepository,mockBetRepository,mockMapper,TestCoroutineDispatcher())

        runBlocking{
            betUseCase.doBet(betModel) shouldBe ApiResultWrapper.Success(value = Unit)
        }
    }

    @Test
    fun `if user create bet and wallet is null return login error works correctly`() {
        val mockMapper = mockk<MapperBetModelToBetRequest>(relaxed = true)
        val mockUserRepository = mockk<UserRepository> {
            coEvery { getLastWallet() } returns null
        }

        val mockBetRepository = mockk<BetRepository> (relaxUnitFun = true)

        val betModel = BetToServerModel(eventId = "1", amount = 20.0, choice = Bet.DRAW)

        val betUseCase = BetUseCase(mockUserRepository,mockBetRepository,mockMapper,
            TestCoroutineDispatcher()
        )

        runBlocking{
            betUseCase.doBet(betModel) shouldBe ApiResultWrapper.Error.LoginError("Login Error")
        }
    }

    @Test
    fun `if user create bet less money than have in wallet works correctly`(){
        val mockMapper = mockk<MapperBetModelToBetRequest> {
            every { map(any()) } returns BetRequest(eventId = "1",choice = "X",amount = 100.0)
        }
        val mockUserRepository = mockk<UserRepository>{
            coEvery {getLastWallet()} returns Wallet(amount = 20.0)
        }

        val mockBetRepository = mockk<BetRepository>(relaxUnitFun = true)
        val betModel = mockk<BetToServerModel>()
        val betUseCase = BetUseCase(mockUserRepository,mockBetRepository,mockMapper,TestCoroutineDispatcher())
        runBlocking {
            betUseCase.doBet(betModel) shouldBe ApiResultWrapper.Error.NoMoneyError(message = "Money Error")
        }
    }

}