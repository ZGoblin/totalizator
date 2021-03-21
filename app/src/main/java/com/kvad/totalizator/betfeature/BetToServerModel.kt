package com.kvad.totalizator.betfeature

import android.os.Parcelable
import com.kvad.totalizator.shared.Bet

data class BetToServerModel (
        val eventId : String,
        val amount : Int,
        val choice : Bet
        )