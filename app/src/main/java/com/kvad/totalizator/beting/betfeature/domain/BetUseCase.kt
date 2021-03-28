package com.kvad.totalizator.beting.betfeature.domain

import com.kvad.totalizator.beting.data.BetRepository
import com.kvad.totalizator.beting.data.MapperBetModelToBetRequest
import com.kvad.totalizator.beting.betfeature.model.BetToServerModel
import com.kvad.totalizator.account.data.UserRepository
import com.kvad.totalizator.beting.data.BetRequest
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import javax.inject.Inject

@Suppress("ReturnCount")
class BetUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val betRepository: BetRepository,
    private val mapperBetModelToBetRequest: MapperBetModelToBetRequest,
) {

    suspend fun doBet(betToServerModel: BetToServerModel): ApiResultWrapper<Unit> {
        val betRequest: BetRequest = mapperBetModelToBetRequest.map(betToServerModel)
        if (getWallet() == null) {
            return ApiResultWrapper.Error.LoginError("Login Error")
        } else if (betRequest.amount > getWallet()!!) {
            return ApiResultWrapper.Error.NoMoneyError("Money Error")
        }
        return betRepository.doBet(betRequest)
    }

    private suspend fun getWallet(): Double? {
        return userRepository.getLastWallet()?.amount
    }

}

