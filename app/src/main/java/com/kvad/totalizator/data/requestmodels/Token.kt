package com.kvad.totalizator.data.requestmodels

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("jwtString") val token: String
)
