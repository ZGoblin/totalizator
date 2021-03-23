package com.kvad.totalizator.shared

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
enum class Bet {
    FIRST_PLAYER_WIN,
    SECOND_PLAYER_WIN,
    DRAW
}
