package com.kvad.totalizator.data

import android.util.Log
import com.kvad.totalizator.betfeature.BetToServerModel
import com.kvad.totalizator.betfeature.MapperBetToString
import com.kvad.totalizator.tools.ResultWrapper
import com.kvad.totalizator.tools.safeApiCall
import retrofit2.Response
import javax.inject.Inject

class BetRepository @Inject constructor(
    private val mapperBetToString: MapperBetToString
) {

    suspend fun doBet(betToServerModel: BetToServerModel): ResultWrapper<Unit> {
        //todo
        val choiceToServer = mapperBetToString.map(betToServerModel.choice)
        Log.d("tag",choiceToServer.toString())
        return safeApiCall {
            Response.success(Unit)
        }
    }
}
