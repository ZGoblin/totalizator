package com.kvad.totalizator.beting.bethistory.ui

import com.kvad.totalizator.beting.bethistory.model.BetHistoryDetailModel
import com.kvad.totalizator.beting.bethistory.model.RequestBetHistoryModel
import com.kvad.totalizator.tools.*
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class BetHistoryMapperTest {

    private val betId = "1"
    private val teamConfrontation = "A vs B"
    private val betTime = "2021-03-28T17:31:55.657+00:00"
    private val eventTime = "2021-03-28T17:48:02.427652+00:00"
    private val betTimeExpected = "28.03, 17:31"
    private val eventTimeExpected = "28.03, 17:48"
    private val amount = 100.0
    private val status = "Bet lost"

    @TestFactory
    fun `map item works correctly`(): List<DynamicTest> {
        val mockMapper = BetHistoryMapper()
        return listOf(
            W1_SERVER_FLAG to W1_BET_CHOICE,
            W2_SERVER_FLAG to W2_BET_CHOICE,
            DRAW_SERVER_FLAG to DRAW_BET_CHOICE,
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("When request have choice is $input, should expected $expected") {
                val actualModel = RequestBetHistoryModel(
                    betId = betId,
                    teamConfrontation = teamConfrontation,
                    choice = input,
                    betTime = betTime,
                    eventStartTime = eventTime,
                    amount = amount,
                    status = status
                )
                val expectedModel = BetHistoryDetailModel(
                    id = betId,
                    teamConfrontation = teamConfrontation,
                    choice = expected,
                    eventStartTime = eventTimeExpected,
                    betStartTime = betTimeExpected,
                    amount = amount,
                    status = status
                )
                mockMapper.mapItem(actualModel) shouldBe expectedModel
            }
        }
    }
}