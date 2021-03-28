package com.kvad.totalizator.data.requestmodels

import com.google.gson.annotations.SerializedName

data class Characteristic(
    @SerializedName("type") val type: String,
    @SerializedName("value") val value: String
)
