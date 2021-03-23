package com.kvad.totalizator.data.requestmodels

import com.google.gson.annotations.SerializedName

data class Wallet(
    @SerializedName("amount") val amount: Int
)
