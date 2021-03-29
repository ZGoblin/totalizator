package com.kvad.totalizator.beting.quickbet.domain

import com.kvad.totalizator.beting.data.BetRepository
import com.kvad.totalizator.beting.data.MapperBetModelToBetRequest
import com.kvad.totalizator.beting.quickbet.model.BetToServerModel
import com.kvad.totalizator.account.data.UserRepository
import com.kvad.totalizator.beting.data.BetRequest
import com.kvad.totalizator.di.DefaultDispatcher
import com.kvad.totalizator.di.IoDispatcher
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Suppress("ReturnCount")
class BetUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val betRepository: BetRepository,
    private val mapperBetModelToBetRequest: MapperBetModelToBetRequest,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun doBet(betToServerModel: BetToServerModel): ApiResultWrapper<Unit> = withContext(dispatcher){
        val betRequest: BetRequest = mapperBetModelToBetRequest.map(betToServerModel)
        if (getWallet() == null) {
            return@withContext ApiResultWrapper.Error.LoginError("Login Error")
        } else if (betRequest.amount > getWallet()!!) {
            return@withContext ApiResultWrapper.Error.NoMoneyError("Money Error")
        }
        return@withContext betRepository.doBet(betRequest)
    }

    private suspend fun getWallet(): Double? = withContext(dispatcher){
        return@withContext userRepository.getLastWallet()?.amount
    }

}

