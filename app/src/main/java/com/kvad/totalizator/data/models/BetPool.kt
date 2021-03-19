package com.kvad.totalizator.data.models

import com.google.gson.annotations.SerializedName

data class BetPool(
    @SerializedName("first_player_amount_bet") val firstAmount: Float,
    @SerializedName("second_player_amount_bet") val secondAmount: Float,
    @SerializedName("draw_amount_bet") val drawAmount: Float,
)
