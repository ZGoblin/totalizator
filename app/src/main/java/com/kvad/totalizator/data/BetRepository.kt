package com.kvad.totalizator.data

import android.util.Log
import com.kvad.totalizator.betfeature.BetToServerModel
import com.kvad.totalizator.betfeature.MapBetToString
import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.safeApiCall
import javax.inject.Inject

class BetRepository @Inject constructor(
    private val mapBetToString: MapBetToString
) {

    suspend fun doBet(betToServerModel: BetToServerModel): ResultWrapper<Unit> {
        Log.d("TAG", "eventId -- ${betToServerModel.eventId}\nchoice -- ${betToServerModel.choice}\namount -- ${betToServerModel.amount}")
        val choiceToServer = mapBetToString.map(betToServerModel.choice)
        Log.d("TAG", "eventId -- ${betToServerModel.eventId}\nchoice -- ${choiceToServer}\namount -- ${betToServerModel.amount}")
        return safeApiCall {
            return@safeApiCall Unit
        }
    }
}