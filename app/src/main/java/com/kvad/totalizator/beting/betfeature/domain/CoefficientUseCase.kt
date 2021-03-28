package com.kvad.totalizator.beting.betfeature.domain

import com.kvad.totalizator.betfeature.model.BetDetail
import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.tools.ANTI_INFINITY_VALUE
import com.kvad.totalizator.tools.MIN_VALUE_FOR_COEF
import javax.inject.Inject
@Suppress("MagicNumber")
class CoefficientUseCase @Inject constructor() {

    fun calculateCoefficient(lastBetDetail: BetDetail, bet: Bet, current: Float): Float {
        val pool = current + lastBetDetail.firstPlayerAmount+lastBetDetail.secondPlayerAmount + lastBetDetail.drawAmount
        val sumMargin = (pool * lastBetDetail.margin) / 100
        val poolWithoutMargin = pool - sumMargin
        val choiceAmount = when (bet) {
            Bet.DRAW -> lastBetDetail.firstPlayerAmount + lastBetDetail.secondPlayerAmount
            Bet.FIRST_PLAYER_WIN -> lastBetDetail.secondPlayerAmount + lastBetDetail.drawAmount
            Bet.SECOND_PLAYER_WIN -> lastBetDetail.firstPlayerAmount + lastBetDetail.drawAmount
        }
        val choiceBet = when (choiceAmount) {
            ANTI_INFINITY_VALUE  -> MIN_VALUE_FOR_COEF
            else -> choiceAmount
        }
        return poolWithoutMargin / (choiceBet + current)
    }

}
