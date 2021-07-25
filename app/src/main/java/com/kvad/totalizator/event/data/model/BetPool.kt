package com.kvad.totalizator.event.data.model

import androidx.compose.ui.graphics.Color

data class BetPool(
    val firstPlayerColorBar: Color,
    val firstPlayerBetAmount: Float,
    val secondPlayerColorBar: Color,
    val secondPlayerBetAmount: Float,
    val drawColorBar: Color,
    val drawBetAmount: Float,
)
