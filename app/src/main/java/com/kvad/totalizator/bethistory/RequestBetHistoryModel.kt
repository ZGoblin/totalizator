package com.kvad.totalizator.bethistory

import com.google.gson.annotations.SerializedName

data class RequestBetHistoryModel (
    @SerializedName("bet_Id") val betId : String,
    @SerializedName("teamConfrontation") val teamConfrontation : String,
    @SerializedName("choice") val choice : String,
    @SerializedName("eventStartime") val eventStartTime : String,
    @SerializedName("betTime") val betTime : String,
    @SerializedName("amount") val amount : Double,
    @SerializedName("status") val status : String,
)