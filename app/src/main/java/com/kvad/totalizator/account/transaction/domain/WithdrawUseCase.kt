package com.kvad.totalizator.account.transaction.domain

import com.kvad.totalizator.account.data.UserRepository
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.account.data.MapperTransactionToTransactionRequest
import com.kvad.totalizator.account.transaction.model.TransactionModel
import com.kvad.totalizator.account.transaction.withdraw.WithdrawRepository
import com.kvad.totalizator.di.DefaultDispatcher
import com.kvad.totalizator.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WithdrawUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val withdrawRepository: WithdrawRepository,
    private val mapperTransactionToTransactionRequest: MapperTransactionToTransactionRequest,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun withdraw(transactionModel: TransactionModel): ApiResultWrapper<Unit> = withContext(dispatcher){
        val transactionRequest = mapperTransactionToTransactionRequest.map(transactionModel)
        if (transactionRequest.amount > getWallet()!!) {
            return@withContext ApiResultWrapper.Error.NoMoneyError("Money Error")
        }
        return@withContext withdrawRepository.doTransaction(transactionRequest)
    }

    private suspend fun getWallet() : Double? = withContext(dispatcher){
        return@withContext userRepository.getLastWallet()?.amount
    }

}
