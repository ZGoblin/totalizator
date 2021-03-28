package com.kvad.totalizator.event.data.requestmodels

import com.google.gson.annotations.SerializedName

data class RequestEventModel(
    @SerializedName("id") val id: String,
    @SerializedName("sportName") val sportName: String,
    @SerializedName("participant1") val participant1: Participant,
    @SerializedName("participant2") val participant2: Participant,
    @SerializedName("startTime") val startTime: String,
    @SerializedName("isEnded") val isEnded: Boolean,
    @SerializedName("possibleResults") val possibleResults: List<String>,
    @SerializedName("margin") val margin: Float,
    @SerializedName("amountW1") val amountW1: Float,
    @SerializedName("amountW2") val amountW2: Float,
    @SerializedName("amountX") val amountX: Float
)
