package com.kvad.totalizator.betfeature.domain

import com.kvad.totalizator.betfeature.model.BetDetail
import com.kvad.totalizator.data.model.BetPool
import com.kvad.totalizator.data.model.Event
import com.kvad.totalizator.data.requestmodels.Characteristic
import com.kvad.totalizator.data.requestmodels.Participant
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.ZonedDateTime

internal class MapperEventToBetDetailModelTest {

    private val eventId = "1"
    private val firstParticipant = Participant(
        id = "2",
        name = "First player",
        photoLink = null,
        characteristics = setOf(Characteristic(type = "weight", value = "70"))
    )
    private val secondParticipant = Participant(
        id = "3",
        name = "Second player",
        photoLink = null,
        characteristics = setOf(Characteristic(type = "weight", value = "75"))
    )

    private val firstPlayerAmount = 200f
    private val secondPlayerAmount = 300f
    private val drawAmount = 400f
    private val margin = 2f

    private val betPool = BetPool(
        firstPlayerBetAmount = firstPlayerAmount,
        secondPlayerBetAmount = secondPlayerAmount,
        drawBetAmount = drawAmount
    )

    @Test
    fun `map event to bet detail model`() {

        val expectedBetDetailModel = BetDetail(
            firstPlayerName = firstParticipant.name,
            secondPlayerName = secondParticipant.name,
            firstPlayerAmount = firstPlayerAmount,
            secondPlayerAmount = secondPlayerAmount,
            drawAmount = drawAmount,
            margin = margin,
            eventId = eventId
        )

        val dataEventModel = Event(
            id = eventId,
            firstParticipant = firstParticipant,
            secondParticipant = secondParticipant,
            startTime = ZonedDateTime.now(),
            isEnded = true,
            isLive = false,
            margin = margin,
            betPool = betPool
        )

        val mapper = MapperEventToBetDetailModel()

        val actualBetDetailModel = mapper.map(dataEventModel)

        assertEquals(expectedBetDetailModel, actualBetDetailModel)
    }
}