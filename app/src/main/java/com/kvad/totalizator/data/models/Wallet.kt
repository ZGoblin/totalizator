package com.kvad.totalizator.data.models

import com.google.gson.annotations.SerializedName

data class Wallet(
    @SerializedName("wallet_id") val walletId: Int,
    @SerializedName("amount") val amount: Int
)
