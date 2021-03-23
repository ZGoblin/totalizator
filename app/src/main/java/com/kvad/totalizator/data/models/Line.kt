package com.kvad.totalizator.data.models

import com.google.gson.annotations.SerializedName

data class Line(
    @SerializedName("events") val events: List<Event>
)
