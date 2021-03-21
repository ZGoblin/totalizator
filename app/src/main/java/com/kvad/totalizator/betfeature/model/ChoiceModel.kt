package com.kvad.totalizator.betfeature.model

import android.os.Parcelable
import com.kvad.totalizator.shared.Bet
import kotlinx.parcelize.Parcelize


@Parcelize
data class ChoiceModel (
    val eventId : String,
    val betState : Bet,
    val firstPlayerName: String,
    val secondPlayerName : String,
):Parcelable
