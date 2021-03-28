package com.kvad.totalizator.betfeature.domain

import com.kvad.totalizator.betfeature.model.BetDetail
import com.kvad.totalizator.beting.betfeature.domain.MapperEventToBetDetailModel
import com.kvad.totalizator.event.data.model.BetPool
import com.kvad.totalizator.event.data.model.Event
import com.kvad.totalizator.event.data.requestmodels.Characteristic
import com.kvad.totalizator.event.data.requestmodels.Participant
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.ZonedDateTime

internal class MapperEventToBetDetailModelTest {


    @Test
    fun `map event to bet detail model`() {

        val eventId = "1"
        val firstParticipant = Participant(
            id = "2",
            name = "First player",
            photoLink = null,
            characteristics = setOf(Characteristic(type = "weight", value = "70"))
        )
        val secondParticipant = Participant(
            id = "3",
            name = "Second player",
            photoLink = null,
            characteristics = setOf(Characteristic(type = "weight", value = "75"))
        )

        val firstPlayerAmount = 200f
        val secondPlayerAmount = 300f
        val drawAmount = 400f
        val margin = 2f

        val betPool = BetPool(
            firstPlayerBetAmount = firstPlayerAmount,
            secondPlayerBetAmount = secondPlayerAmount,
            drawBetAmount = drawAmount
        )

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