package com.kvad.totalizator.onboard

import com.kvad.totalizator.R
import com.kvad.totalizator.onboard.model.BoardInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BoardInfoRepository @Inject constructor() {

    val boardInfoList = listOf(
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
