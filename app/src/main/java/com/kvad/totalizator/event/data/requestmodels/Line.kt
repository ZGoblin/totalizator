package com.kvad.totalizator.event.data.requestmodels

import com.google.gson.annotations.SerializedName

data class Line(
    @SerializedName("events") val events: List<RequestEventModel>
)
