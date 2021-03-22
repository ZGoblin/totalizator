package com.kvad.totalizator.betfeature

import android.util.Log
import com.kvad.totalizator.data.BetRepository
import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.models.Wallet
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import javax.inject.Inject

//TODO add verify with wallet
class BetUseCase @Inject constructor(
    private val betRepository: BetRepository,
    private val mapperBetModelToBetRequest: MapperBetModelToBetRequest
) {
    suspend fun bet(betToServerModel: BetToServerModel) : ResultWrapper<Unit> {
        val betRequest = mapperBetModelToBetRequest.map(betToServerModel)
        Log.d("TAG","betRequest -- ${betRequest.eventId.toString()}")
        return betRepository.doBet(betRequest)
    }
}