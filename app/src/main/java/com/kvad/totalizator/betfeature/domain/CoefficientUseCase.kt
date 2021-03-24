package com.kvad.totalizator.betfeature.domain

import com.kvad.totalizator.betfeature.model.BetDetail
import com.kvad.totalizator.shared.Bet
import javax.inject.Inject

class CoefficientUseCase @Inject constructor() {

    fun calculateCoefficient(lastBetDetail: BetDetail, bet: Bet, current : Float): Float {
        val pool = current + lastBetDetail.firstPlayerAmount + lastBetDetail.secondPlayerAmount + lastBetDetail.drawAmount
        val sumMargin = (pool * lastBetDetail.margin)/100
        val poolWithoutMargin = pool - sumMargin
        val choiceAmount = when (bet) {
            Bet.DRAW -> lastBetDetail.firstPlayerAmount + lastBetDetail.secondPlayerAmount
            Bet.FIRST_PLAYER_WIN -> lastBetDetail.secondPlayerAmount + lastBetDetail.drawAmount
            Bet.SECOND_PLAYER_WIN -> lastBetDetail.firstPlayerAmount + lastBetDetail.drawAmount
        }
        val choiceBet = when (choiceAmount) {
            0.0f -> 1.0f
            else -> choiceAmount
        }
        val result = poolWithoutMargin / (choiceBet + current)
        return result
    }

}
