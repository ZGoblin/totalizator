package com.kvad.totalizator.data.models

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("id") val id: String,
    @SerializedName("sportName") val sportName: String,
    @SerializedName("participant1") val participant1: ParticipantDTO,
    @SerializedName("participant2") val participant2: ParticipantDTO,
    @SerializedName("startTime") val startTime: String,
    @SerializedName("isEnded") val isEnded: Boolean,
    @SerializedName("possibleResults") val possibleResults: List<String>,
    @SerializedName("margin") val margin: Float,
    @SerializedName("amountW1") val amountW1: Float,
    @SerializedName("amountW2") val amountW2: Float,
    @SerializedName("amountX") val amountX: Float
)
