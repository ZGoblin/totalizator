package com.kvad.totalizator.data

import android.util.Log
import com.kvad.totalizator.betfeature.BetToServerModel
import com.kvad.totalizator.betfeature.MapperBetToString
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.safeApiCall
import javax.inject.Inject

class BetRepository @Inject constructor(
    private val mapperBetToString: MapperBetToString
) {

    suspend fun doBet(betToServerModel: BetToServerModel): ResultWrapper<Unit> {
        val choiceToServer = mapperBetToString.map(betToServerModel.choice)
        Log.d("TAG", "${betToServerModel.eventId} $choiceToServer ${betToServerModel.amount}")
        return safeApiCall {
            return@safeApiCall Unit
        }
    }
}
