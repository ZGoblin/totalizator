package com.kvad.totalizator.betfeature

import com.kvad.totalizator.shared.Bet

data class BetToServerModel (
    val eventId: String,
    val amount: Double,
    val choice: Bet
)
