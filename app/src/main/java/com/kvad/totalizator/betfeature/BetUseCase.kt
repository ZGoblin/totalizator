package com.kvad.totalizator.betfeature

import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.requestmodels.Wallet
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import javax.inject.Inject

//TODO add verify with wallet
class BetUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val mapperBetModelToBetRequest: MapperBetModelToBetRequest
) {

    private var wallet: Wallet? = null
    private lateinit var state : BetState

    suspend fun bet(betToServerModel: BetToServerModel): ApiResultWrapper<Unit> {
        val betRequest = mapperBetModelToBetRequest.map(betToServerModel)
        return userRepository.doBet(betRequest)
    }

    //TODO
//    private suspend fun verifyWithWallet(betRequest: BetRequest): BetState {
//        return withContext(Dispatchers.Default) {
//            Log.d("Tag", getWalletAmount().toString())
//            when {
//                betRequest.amount < wallet -> BetState.BET_AMOUNT_MORE_THEN_WALLET
//                wallet <= 1.0 -> BetState.NO_MONEY_LEFT
//                else -> BetState.NO_MONEY_LEFT
//            }
//        }
//    }
//
//    private suspend fun getWalletAmount(): Wallet? {
//        userRepository.wallet().single().doOnResult(
//            onSuccess = ::doOnSuccess,
//            onError = ::doOnError
//        )
//        return wallet
//    }
//
//    private fun doOnError(error : ApiResultWrapper.Error){
//        state = BetState.NO_MONEY_LEFT
//    }
//
//    private fun doOnSuccess(wallet: Wallet) {
//        this.wallet = wallet
//        state = BetState.WITHOUT_ERROR
//    }
}
