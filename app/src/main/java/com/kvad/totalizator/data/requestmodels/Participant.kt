package com.kvad.totalizator.data.requestmodels

import com.google.gson.annotations.SerializedName

data class Participant(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("photo_link") val photoLink: String?,
    @SerializedName("parameters") val characteristics: Set<Characteristic>
)
