package com.kvad.totalizator.bethistory.ui



import com.kvad.totalizator.beting.bethistory.model.BetHistoryDetailModel
import com.kvad.totalizator.beting.bethistory.model.RequestBetHistoryModel
import com.kvad.totalizator.beting.bethistory.ui.BetHistoryMapper
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory


internal class BetHistoryMapperTest {

    private val id = "1"
    private val teamConfrontation = "England vs France"
    private val eventStartTimeFromRequest = "2021-03-28T12:22:50.187+03:00"
    private val betStartTimeFromRequest = "2021-03-28T12:22:50.187+03:00"
    private val eventStartTime = "22:50,28.03"
    private val betStartTime = "22:50,28.03"
    private val amount = 100.0
    private val status = "status"

    @TestFactory
    fun `map request bet history to bet history model`(): List<DynamicTest> {
        val mapper = BetHistoryMapper()

        return listOf(
            "W1" to "1",
            "W2" to "2",
            "X" to "X"
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("When input $input, bet history model must be $expected") {
                mapper.mapItem(
                    RequestBetHistoryModel(
                        betId = id,
                        teamConfrontation = teamConfrontation,
                        choice = input,
                        eventStartTime = eventStartTimeFromRequest,
                        betTime = betStartTimeFromRequest,
                        amount = amount,
                        status = status
                    )
                ) shouldBe BetHistoryDetailModel(
                    id = id,
                    teamConfrontation = teamConfrontation,
                    choice = expected,
                    eventStartTime = eventStartTime,
                    betStartTime = betStartTime,
                    amount = amount,
                    status = status
                )
            }
        }
    }

}