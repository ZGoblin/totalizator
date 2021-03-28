package com.kvad.totalizator.beting.data

import com.google.gson.annotations.SerializedName

data class BetRequest(
    @SerializedName("event_Id") val eventId: String,
    @SerializedName("choice") val choice: String,
    @SerializedName("amount") val amount: Double
)
