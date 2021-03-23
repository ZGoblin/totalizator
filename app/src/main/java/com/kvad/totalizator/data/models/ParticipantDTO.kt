package com.kvad.totalizator.data.models

import com.google.gson.annotations.SerializedName

data class ParticipantDTO(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("photo_link") val photoLink: String,
    @SerializedName("parameters") val characteristics: Set<Characteristic>
)
