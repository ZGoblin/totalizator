package com.kvad.totalizator.bethistory

data class BetHistoryDetailModel(
    val id : String,
    val teamConfrontation: String,
    val choice: BetChoice,
    val eventStartTime: String,
    val betStartTime: String,
    val amount: Double,
    val status: BetStatus
)
