package com.kvad.totalizator.data.models

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("id") val id: String,
    @SerializedName("sport") val sport: String,
    @SerializedName("participantdto1") val participantDto1: ParticipantDTO,
    @SerializedName("participantdto2") val participantDto2: ParticipantDTO,
    @SerializedName("bet_pool") val betPool: BetPool
)
