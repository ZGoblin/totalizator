package com.kvad.totalizator.shared

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("jwtString") val token: String
)
