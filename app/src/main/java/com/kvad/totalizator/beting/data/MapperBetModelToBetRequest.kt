package com.kvad.totalizator.beting.data

import com.kvad.totalizator.beting.betfeature.model.BetToServerModel
import com.kvad.totalizator.beting.data.BetRequest
import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.tools.DRAW_SERVER_FLAG
import com.kvad.totalizator.tools.W1_SERVER_FLAG
import com.kvad.totalizator.tools.W2_SERVER_FLAG
import javax.inject.Inject

class MapperBetModelToBetRequest @Inject constructor() {

    fun map(betModel: BetToServerModel): BetRequest {
        return BetRequest(
            eventId = betModel.eventId,
            choice = when (betModel.choice) {
                Bet.DRAW -> DRAW_SERVER_FLAG
                Bet.FIRST_PLAYER_WIN -> W1_SERVER_FLAG
                Bet.SECOND_PLAYER_WIN -> W2_SERVER_FLAG
            },
            amount = betModel.amount
        )
    }
}

