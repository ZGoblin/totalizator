package com.kvad.totalizator.beting.quickbet.domain

import com.kvad.totalizator.beting.bethistory.quickbet.model.BetDetail
import com.kvad.totalizator.shared.Bet
import javax.inject.Inject

class CoefficientUseCase @Inject constructor() {

    fun calculateCoefficient(lastBetDetail: BetDetail, bet: Bet, current: Float): Float {

        val margePercent = 1 - lastBetDetail.margin / 100F
        var winPool = 0F
        var loosePool = 0F

        when (bet) {
            Bet.FIRST_PLAYER_WIN -> {
                winPool = lastBetDetail.firstPlayerAmount
                loosePool = lastBetDetail.secondPlayerAmount + lastBetDetail.drawAmount
            }
            Bet.SECOND_PLAYER_WIN -> {
                winPool = lastBetDetail.secondPlayerAmount
                loosePool = lastBetDetail.firstPlayerAmount + lastBetDetail.drawAmount
            }
            Bet.DRAW -> {
                winPool = lastBetDetail.drawAmount
                loosePool = lastBetDetail.firstPlayerAmount + lastBetDetail.secondPlayerAmount
            }
        }

        winPool *= margePercent
        loosePool *= margePercent
        val betWithMargin = current * margePercent

        val share = betWithMargin / (winPool + betWithMargin)

        return (loosePool * share) + betWithMargin
    }

}
