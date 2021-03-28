package com.kvad.totalizator.beting.bethistory.model

data class BetHistoryDetailModel(
    val id : String,
    val teamConfrontation: String,
    val choice: String,
    val eventStartTime: String,
    val betStartTime: String,
    val amount: Double,
    val status: String
)
