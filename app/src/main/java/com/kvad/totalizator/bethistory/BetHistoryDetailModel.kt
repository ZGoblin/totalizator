package com.kvad.totalizator.bethistory

import java.time.ZonedDateTime

data class BetHistoryDetailModel(
    val id : String,
    val teamConfrontation: String,
    val choice: BetChoice,
    val eventStartTime: ZonedDateTime,
    val betStartTime: ZonedDateTime,
    val amount: Double,
    val status: BetStatus
)
