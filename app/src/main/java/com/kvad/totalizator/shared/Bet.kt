package com.kvad.totalizator.shared

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Bet : Parcelable {
    FIRST_PLAYER_WIN,
    SECOND_PLAYER_WIN,
    DRAW
}
