package com.kvad.totalizator.betfeature

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChoiceModel (
    val choiceState : ChoiceState,
    val commandFirst: CommandInfoSum,
    val commandSecond : CommandInfoSum,
):Parcelable