package com.kvad.totalizator.bethistory

import com.kvad.totalizator.tools.W1_SERVER_FLAG
import com.kvad.totalizator.tools.W2_SERVER_FLAG
import java.time.ZonedDateTime
import javax.inject.Inject

class BetHistoryMapper @Inject constructor() {

    fun map(requestBetHistoryModel: RequestBetHistoryModel) = BetHistoryDetailModel(
        id = requestBetHistoryModel.betId,
        teamConfrontation = requestBetHistoryModel.teamConfrontation,
        choice = when (requestBetHistoryModel.choice) {
            W1_SERVER_FLAG -> BetChoice.FIRST_PLAYER_WIN
            W2_SERVER_FLAG -> BetChoice.SECOND_PLAYER_WIN
            else -> BetChoice.DRAW
        },
        eventStartTime = parseZonedDateTime(requestBetHistoryModel.eventStartTime),
        betStartTime = parseZonedDateTime(requestBetHistoryModel.betTime),
        amount = requestBetHistoryModel.amount,
        status = when (requestBetHistoryModel.status) {
            "Bet lost" -> BetStatus.LOSE
            else -> BetStatus.WIN
        }

    )

    fun map(betHistoryPreview: List<RequestBetHistoryModel>): List<BetHistoryDetailModel> {
        return betHistoryPreview.map { map(it) }
    }

    private fun parseZonedDateTime(time: String): ZonedDateTime {
        return ZonedDateTime.parse(time)
    }
}
