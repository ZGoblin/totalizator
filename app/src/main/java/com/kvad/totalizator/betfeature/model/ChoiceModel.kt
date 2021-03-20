package com.kvad.totalizator.betfeature.model

import android.os.Parcelable
import com.kvad.totalizator.betfeature.ChoiceState
import com.kvad.totalizator.betfeature.CommandInfoSum
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChoiceModel (
    val choiceState : ChoiceState,
    val commandFirst: CommandInfoSum,
    val commandSecond : CommandInfoSum,
):Parcelable