package com.kvad.totalizator.beting.bethistory.quickbet.domain

import com.kvad.totalizator.beting.bethistory.quickbet.model.BetDetail
import com.kvad.totalizator.beting.quickbet.domain.CoefficientUseCase
import com.kvad.totalizator.shared.Bet
import io.kotlintest.shouldBe
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class CoefficientUseCaseTest {

    private val lastBetDetail = BetDetail(
        firstPlayerName = "Player A",
        secondPlayerName = "Player B",
        firstPlayerAmount = 1496f,
        secondPlayerAmount = 621f,
        drawAmount = 450f,
        margin = 5f,
        eventId = "1"
    )

    private val currentPlacedBet = 20f
    private val coefficientUseCase = CoefficientUseCase()

    @TestFactory
    fun `calculate coefficient works correctly`(): List<DynamicTest> {

        return listOf(
            Bet.DRAW to 104.58085f,
            Bet.FIRST_PLAYER_WIN to 32.422825f,
            Bet.SECOND_PLAYER_WIN to 76.68175f,

            ).map { (input, expected) ->
            DynamicTest.dynamicTest("When choice $input, coefficient should be $expected") {
                coefficientUseCase.calculateCoefficient(
                    lastBetDetail,
                    input,
                    currentPlacedBet
                ) shouldBe expected
            }

        }
    }

}