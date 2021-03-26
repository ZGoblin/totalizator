package com.kvad.totalizator.bethistory

import com.kvad.totalizator.tools.W1_SERVER_FLAG
import com.kvad.totalizator.tools.W2_SERVER_FLAG
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
        eventStartTime = requestBetHistoryModel.eventStartTime,
        betStartTime = requestBetHistoryModel.betTime,
        amount = requestBetHistoryModel.amount,
        status = when (requestBetHistoryModel.status) {
            "WIN" -> BetStatus.WIN
            else -> BetStatus.LOSE
        }

    )

    fun map(betHistoryPreview: List<RequestBetHistoryModel>): List<BetHistoryDetailModel> {
        return betHistoryPreview.map { map(it) }
    }
}
