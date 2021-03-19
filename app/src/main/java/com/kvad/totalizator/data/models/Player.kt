package com.kvad.totalizator.data.models

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("age") val age: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("height") val height: Int
)
