package com.kvad.totalizator.data.models

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("login") val login: String,
    @SerializedName("password") val password: String
)
