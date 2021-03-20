package com.kvad.totalizator.betfeature

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommandInfoSum(
    val name: String,
    val sum: Double
) : Parcelable