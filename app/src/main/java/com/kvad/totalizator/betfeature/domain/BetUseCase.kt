package com.kvad.totalizator.betfeature.domain

import com.kvad.totalizator.betfeature.BetRepository
import com.kvad.totalizator.betfeature.data.MapperBetModelToBetRequest
import com.kvad.totalizator.betfeature.model.BetToServerModel
import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.requestmodels.BetRequest
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import javax.inject.Inject

class BetUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val betRepository: BetRepository,
    private val mapperBetModelToBetRequest: MapperBetModelToBetRequest,
) {

    suspend fun doBet(betToServerModel: BetToServerModel): ApiResultWrapper<Unit> {
        val betRequest: BetRequest = mapperBetModelToBetRequest.map(betToServerModel)
        if (betRequest.amount > getWallet()) {
            return ApiResultWrapper.Error.NoMoneyError("Money Error")
        }
        return betRepository.doBet(betRequest)
    }

    private suspend fun getWallet(): Double {
        return userRepository.getLastWallet().amount
    }

}

