package com.kvad.totalizator.data.models

import com.google.gson.annotations.SerializedName

data class ParticipantDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("photo_link") val photoLink: String,
    @SerializedName("player") val player: Player,
)
