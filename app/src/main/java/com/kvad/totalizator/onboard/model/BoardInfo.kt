package com.kvad.totalizator.onboard.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.kvad.totalizator.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BoardInfo (
    val titles: List<Int>,
    @StringRes val body: Int
) : Parcelable {
    companion object {
        val items: List<BoardInfo> = listOf(
            BoardInfo(
                listOf(R.string.players_vs_players_title),
                R.string.players_vs_players_body
            ),
            BoardInfo(
                listOf(R.string.chat_troll_provoke_title),
                R.string.chat_troll_provoke_body
            ),
            BoardInfo(
                listOf(
                    R.string.less_commission_title_1,
                    R.string.less_commission_title_2,
                ), R.string.less_commission_body
            )
        )
    }
}
