package com.kvad.totalizator.betfeature.domain

import android.util.Log
import com.kvad.totalizator.betfeature.BetState
import com.kvad.totalizator.betfeature.data.MapperBetModelToBetRequest
import com.kvad.totalizator.betfeature.model.BetToServerModel
import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.requestmodels.Wallet
import com.kvad.totalizator.di.DefaultDispatcher
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.withContext
import java.lang.Error
import javax.inject.Inject

class BetUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val mapperBetModelToBetRequest: MapperBetModelToBetRequest,
) {

    suspend fun doBet(betToServerModel: BetToServerModel): ApiResultWrapper<Unit> {
        val betRequest = mapperBetModelToBetRequest.map(betToServerModel)
        return userRepository.doBet(betRequest)
    }

    private suspend fun getWallet(): Double {
        return userRepository.wallet().single().asSuccess().value.amount
    }

}
