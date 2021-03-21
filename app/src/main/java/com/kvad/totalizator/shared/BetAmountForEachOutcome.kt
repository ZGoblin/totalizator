package com.kvad.totalizator.shared


data class BetAmountForEachOutcome(
    val firstPlayerWinBetAmount: Int,
    val draw: Int,
    val secondPlayerWinBetAmount: Int,
)
