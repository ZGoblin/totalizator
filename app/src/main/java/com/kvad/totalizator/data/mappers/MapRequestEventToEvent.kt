package com.kvad.totalizator.data.mappers

import com.kvad.totalizator.data.model.Event
import com.kvad.totalizator.data.model.BetPool
import com.kvad.totalizator.data.requestmodels.RequestEventModel
import java.time.ZonedDateTime
import javax.inject.Inject

class MapRequestEventToEvent @Inject constructor() {

    fun map(requestEventModel: RequestEventModel) = Event(
        id = requestEventModel.id,
        firstParticipant = requestEventModel.participant1,
        secondParticipant = requestEventModel.participant2,
        startTime = parseZonedDateTime(requestEventModel.startTime),
        isEnded = requestEventModel.isEnded,
        margin = requestEventModel.margin,
        betPool = BetPool(
            firstPlayerBetAmount = requestEventModel.amountW1,
            secondPlayerBetAmount = requestEventModel.amountW2,
            drawBetAmount = requestEventModel.amountX
        )
    )

    fun map(requestEventModelList: List<RequestEventModel>): List<Event> {
        return requestEventModelList.map { map(it) }
    }

    //TODO delete if date always not null
    private fun parseZonedDateTime(time: String): ZonedDateTime {
        if (time.isNotEmpty()) {
            return ZonedDateTime.parse(time)
        }
        return ZonedDateTime.now()
    }
}
