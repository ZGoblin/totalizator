package com.kvad.totalizator.account.data.model

import com.google.gson.annotations.SerializedName

data class Wallet(
    @SerializedName("amount") val amount: Double
)
