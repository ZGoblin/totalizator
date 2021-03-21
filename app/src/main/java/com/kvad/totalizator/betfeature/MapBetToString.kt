package com.kvad.totalizator.betfeature

import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.tools.DRAW_SERVER_FLAG
import com.kvad.totalizator.tools.W1_SERVER_FLAG
import com.kvad.totalizator.tools.W2_SERVER_FLAG
import javax.inject.Inject

class MapBetToString @Inject constructor() {

    fun map(bet: Bet): String {
        return when (bet) {
            Bet.DRAW -> DRAW_SERVER_FLAG
            Bet.FIRST_PLAYER_WIN -> W1_SERVER_FLAG
            Bet.SECOND_PLAYER_WIN -> W2_SERVER_FLAG
        }
    }
}

