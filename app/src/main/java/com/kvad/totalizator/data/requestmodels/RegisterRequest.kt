package com.kvad.totalizator.data.requestmodels

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("dob") val dob: String
)
