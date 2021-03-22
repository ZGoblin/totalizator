package com.kvad.totalizator.data

import com.kvad.totalizator.betfeature.BetRequest
import com.kvad.totalizator.betfeature.BetService
import com.kvad.totalizator.betfeature.BetToServerModel
import com.kvad.totalizator.betfeature.MapperBetModelToBetRequest
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.safeApiCall
import javax.inject.Inject

class BetRepository @Inject constructor(
    private val betService: BetService
) {

    suspend fun doBet(betRequest: BetRequest): ResultWrapper<Unit> {
        return safeApiCall {
            betService.doBet(betRequest)
        }
    }

}
