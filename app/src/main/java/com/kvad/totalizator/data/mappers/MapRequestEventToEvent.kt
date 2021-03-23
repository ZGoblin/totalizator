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
        startTime = ZonedDateTime.parse(requestEventModel.startTime),
        isEnded = requestEventModel.isEnded,
        margin = requestEventModel.margin,
        betPool = BetPool(
            firstPlayerBetAmount = requestEventModel.amountW1,
            secondPlayerBetAmount = requestEventModel.amountX,
            drawBetAmount = requestEventModel.amountW2
        )
    )

    fun map(requestEventModelList: List<RequestEventModel>): List<Event> {
        return requestEventModelList.map { map(it) }
    }

}
