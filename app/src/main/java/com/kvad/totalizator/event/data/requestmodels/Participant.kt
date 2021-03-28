package com.kvad.totalizator.event.data.requestmodels

import com.google.gson.annotations.SerializedName

data class Participant(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("photoLink") val photoLink: String?,
    @SerializedName("parameters") val characteristics: Set<Characteristic>
)
