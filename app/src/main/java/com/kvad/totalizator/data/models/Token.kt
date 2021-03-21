package com.kvad.totalizator.data.models

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("jwtString") val token: String
)