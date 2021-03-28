package com.kvad.totalizator.beting.bethistory.quickbet.data

import com.kvad.totalizator.beting.quickbet.model.BetToServerModel
import com.kvad.totalizator.beting.data.BetRequest
import com.kvad.totalizator.beting.data.MapperBetModelToBetRequest
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
            Bet.DRAW to DRAW_SERVER_FLAG,
            Bet.FIRST_PLAYER_WIN to W1_SERVER_FLAG,
            Bet.SECOND_PLAYER_WIN to W2_SERVER_FLAG,
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("When input are $input, result should be $expected") {
                val actualModel = BetToServerModel(
                    eventId = eventId,
                    amount = amount,
                    choice = input
                )

                val expectedModel = BetRequest(
                    eventId = eventId,
                    amount = amount,
                    choice = expected
                )
                mapper.map(actualModel) shouldBe expectedModel
            }
        }
    }

}