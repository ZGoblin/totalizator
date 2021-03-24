package com.kvad.totalizator.betfeature.domain

import com.kvad.totalizator.betfeature.data.MapperBetModelToBetRequest
import com.kvad.totalizator.betfeature.model.BetToServerModel
import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import javax.inject.Inject

class BetUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val mapperBetModelToBetRequest: MapperBetModelToBetRequest
) {

    suspend fun doBet(betToServerModel: BetToServerModel): ApiResultWrapper<Unit> {
        val betRequest = mapperBetModelToBetRequest.map(betToServerModel)
        return userRepository.doBet(betRequest)
    }

}
