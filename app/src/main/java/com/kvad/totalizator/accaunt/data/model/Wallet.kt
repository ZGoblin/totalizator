package com.kvad.totalizator.accaunt.data

import com.google.gson.annotations.SerializedName

data class Wallet(
    @SerializedName("amount") val amount: Double
)
