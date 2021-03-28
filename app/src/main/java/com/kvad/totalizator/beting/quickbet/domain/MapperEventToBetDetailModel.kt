package com.kvad.totalizator.beting.quickbet.domain

import com.kvad.totalizator.beting.bethistory.quickbet.model.BetDetail
import com.kvad.totalizator.event.data.model.Event
import javax.inject.Inject

class MapperEventToBetDetailModel @Inject constructor() {

    fun map(event: Event): BetDetail {
        return BetDetail(
            firstPlayerName = event.firstParticipant.name,
            secondPlayerName = event.secondParticipant.name,
            firstPlayerAmount = event.betPool.firstPlayerBetAmount,
            secondPlayerAmount = event.betPool.secondPlayerBetAmount,
            drawAmount = event.betPool.drawBetAmount,
            margin = event.margin,
            eventId = event.id

        )

    }

}
