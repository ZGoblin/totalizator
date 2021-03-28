package com.kvad.totalizator.accaunt.data.model

import com.google.gson.annotations.SerializedName

data class Wallet(
    @SerializedName("amount") val amount: Double
)
