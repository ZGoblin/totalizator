package com.kvad.totalizator.betfeature

import android.util.Log
import com.kvad.totalizator.data.BetRepository
import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.withContext
import javax.inject.Inject

//TODO add verify with wallet
class BetUseCase @Inject constructor(
    private val betRepository: BetRepository,
    private val userRepository: UserRepository,
    private val mapperBetModelToBetRequest: MapperBetModelToBetRequest
) {

    private lateinit var state : BetState

    suspend fun bet(betToServerModel: BetToServerModel) : ApiResultWrapper<Unit> {
        val betRequest = mapperBetModelToBetRequest.map(betToServerModel)
        return betRepository.doBet(betRequest)
    }
//TODO
//    private fun onError() : ApiResultWrapper.Error{
//        return when(state){
//            BetState.NO_MONEY_LEFT -> ApiResultWrapper.Error.UnknownError(message = "No money")
//            BetState.BET_AMOUNT_MORE_THEN_WALLET -> ApiResultWrapper.Error.UnknownError(message = "Less money")
//            BetState.WITHOUT_ERROR -> TODO()
//        }
//    }
//
//    private suspend fun verifyWithWallet(betRequest: BetRequest): BetState =
//        withContext(Dispatchers.Default) {
//            val wallet = getWallet()
//            Log.d("Tag",getWallet().toString())
//            when {
//                betRequest.amount < wallet -> BetState.BET_AMOUNT_MORE_THEN_WALLET
//                wallet <= 1.0 -> BetState.NO_MONEY_LEFT
//                else -> BetState.NO_MONEY_LEFT
//            }
//        }
//
//    private suspend fun getWallet(): Double {
//        return betRepository.walletForAmount().asSuccess().value.amount.toDouble()
//    }
}
