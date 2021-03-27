package com.kvad.totalizator.betfeature.data

import com.kvad.totalizator.betfeature.model.BetToServerModel
import com.kvad.totalizator.data.requestmodels.BetRequest
import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.tools.DRAW_SERVER_FLAG
import com.kvad.totalizator.tools.W1_SERVER_FLAG
import com.kvad.totalizator.tools.W2_SERVER_FLAG
import io.kotlintest.shouldBe
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class MapperBetModelToBetRequestTest {

    private val eventId = "1"
    private val amount = 100.0

    @TestFactory
    fun `map bet model to bet request model`(): List<DynamicTest> {
        val mapper = MapperBetModelToBetRequest()
        return listOf(
            BetToServerModel(
                eventId = eventId,
                amount = amount,
                choice = Bet.DRAW
            ) to BetRequest(
                eventId = eventId,
                amount = amount,
                choice = DRAW_SERVER_FLAG
            ), BetToServerModel(
                eventId = eventId,
                amount = amount,
                choice = Bet.FIRST_PLAYER_WIN
            ) to BetRequest(
                eventId = eventId,
                amount = amount,
                choice = W1_SERVER_FLAG
            ), BetToServerModel(
                eventId = eventId,
                amount = amount,
                choice = Bet.SECOND_PLAYER_WIN
            ) to BetRequest(
                eventId = eventId,
                amount = amount,
                choice = W2_SERVER_FLAG
            )
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("When input are $input, bet request should be $expected") {
                mapper.map(input) shouldBe expected
            }
        }
    }

}