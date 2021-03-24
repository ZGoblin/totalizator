package com.kvad.totalizator.betfeature

import com.kvad.totalizator.data.model.Event
import com.kvad.totalizator.shared.Bet
import javax.inject.Inject

class CoefficientUseCase @Inject constructor() {

    fun calculateCoefficient(lastBetDetail: BetDetail, bet: Bet): Float {
        val pool = lastBetDetail.firstPlayerAmount + lastBetDetail.secondPlayerAmount + lastBetDetail.drawAmount
        val sumMargin = pool / 100 * lastBetDetail.margin
        val poolWithoutMargin = pool - sumMargin
        val choice = when (bet) {
            Bet.DRAW -> lastBetDetail.drawAmount
            Bet.FIRST_PLAYER_WIN -> lastBetDetail.firstPlayerAmount
            Bet.SECOND_PLAYER_WIN -> lastBetDetail.secondPlayerAmount
        }
        val choiceBet = when (choice) {
            0.0f -> 1.0f
            else -> choice
        }
        return (poolWithoutMargin / choiceBet)
    }

}