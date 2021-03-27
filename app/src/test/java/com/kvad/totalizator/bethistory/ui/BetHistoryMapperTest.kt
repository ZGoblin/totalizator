package com.kvad.totalizator.bethistory.ui

import com.kvad.totalizator.bethistory.model.BetHistoryDetailModel
import com.kvad.totalizator.bethistory.model.RequestBetHistoryModel
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.time.ZonedDateTime

internal class BetHistoryMapperTest {

    private fun parseZonedDateTime(time: String): String {
        val zonedDateTime = ZonedDateTime.parse(time)
        return "${zonedDateTime.dayOfMonth}.${zonedDateTime.monthValue}, ${zonedDateTime.hour}:${zonedDateTime.minute}"
    }


    private val id = "1"
    private val teamConfrontation = "England vs France"
    private val eventStartTime = parseZonedDateTime( ZonedDateTime.now().toString())
    private val betStartTime = parseZonedDateTime( ZonedDateTime.now().toString())
    private val amount = 100.0
    private val status = "status"

    @TestFactory
    fun `map request bet history to bet history model`(): List<DynamicTest> {
        val mapper = BetHistoryMapper()
        return listOf(
            RequestBetHistoryModel(
                betId = id,
                teamConfrontation = teamConfrontation,
                choice = "W1",
                eventStartTime = eventStartTime,
                betTime = betStartTime,
                amount = amount,
                status = status
            ) to BetHistoryDetailModel(
                id = id,
                teamConfrontation = teamConfrontation,
                choice = "1",
                eventStartTime = eventStartTime,
                betStartTime = betStartTime,
                amount = amount,
                status = status
            ),
            RequestBetHistoryModel(
                betId = id,
                teamConfrontation = teamConfrontation,
                choice = "W2",
                eventStartTime = eventStartTime,
                betTime = betStartTime,
                amount = amount,
                status = status
            ) to BetHistoryDetailModel(
                id = id,
                teamConfrontation = teamConfrontation,
                choice = "2",
                eventStartTime = eventStartTime,
                betStartTime = betStartTime,
                amount = amount,
                status = status
            ),
            RequestBetHistoryModel(
                betId = id,
                teamConfrontation = teamConfrontation,
                choice = "X",
                eventStartTime = eventStartTime,
                betTime = betStartTime,
                amount = amount,
                status = status
            ) to BetHistoryDetailModel(
                id = id,
                teamConfrontation = teamConfrontation,
                choice = "X",
                eventStartTime = eventStartTime,
                betStartTime = betStartTime,
                amount = amount,
                status = status
            )
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("When input $input, bet history model must be $expected") {
                mapper.mapItem(input) shouldBe expected
            }
        }
    }

}