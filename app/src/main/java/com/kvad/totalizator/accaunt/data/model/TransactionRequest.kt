package com.kvad.totalizator.accaunt.data.model

import com.google.gson.annotations.SerializedName

data class TransactionRequest(
    @SerializedName("amount") val amount: Double,
    @SerializedName("type") val type: String
)
