package com.kvad.totalizator.data.models

import com.google.gson.annotations.SerializedName

data class Wallet(
    @SerializedName("amount") val amount: Int
)
