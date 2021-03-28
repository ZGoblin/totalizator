package com.kvad.totalizator.beting.bethistory.quickbet.domain

import com.kvad.totalizator.beting.bethistory.quickbet.model.BetDetail
import com.kvad.totalizator.beting.quickbet.domain.CoefficientUseCase
import com.kvad.totalizator.shared.Bet
import io.kotlintest.shouldBe
import io.mockk.mockk
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class CoefficientUseCaseTest {

    private val lastBetDetail = BetDetail(
        firstPlayerName = "Player A",
        secondPlayerName = "Player B",
        firstPlayerAmount = 150f,
        secondPlayerAmount = 200f,
        drawAmount = 100f,
        margin = 2f,
        eventId = "1"
    )

    private val currentPlacedBet = 50f
    private val coefficientUseCase = CoefficientUseCase()

    @TestFactory
    fun `calculate coefficient works correctly`(): List<DynamicTest> {

        return listOf(
            Bet.DRAW to 1.225f,
            Bet.FIRST_PLAYER_WIN to 1.4f,
            Bet.SECOND_PLAYER_WIN to 1.6333333f,

            ).map { (input, expected) ->
            DynamicTest.dynamicTest("When choice $input,should be $expected") {
                coefficientUseCase.calculateCoefficient(
                    lastBetDetail,
                    input,
                    currentPlacedBet
                ) shouldBe expected
            }

        }
    }

}