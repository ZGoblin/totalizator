package com.kvad.totalizator.onboard.model

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class BoardInfo(
    val titles: List<Int>,
    @StringRes val body: Int
) : Parcelable
