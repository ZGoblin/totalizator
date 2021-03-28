package com.kvad.totalizator.account.transaction.domain

import com.kvad.totalizator.account.data.UserRepository
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.account.data.MapperTransactionToTransactionRequest
import com.kvad.totalizator.account.transaction.model.TransactionModel
import com.kvad.totalizator.account.transaction.withdraw.WithdrawRepository
import javax.inject.Inject

class WithdrawUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val withdrawRepository: WithdrawRepository,
    private val mapperTransactionToTransactionRequest: MapperTransactionToTransactionRequest
) {

    suspend fun withdraw(transactionModel: TransactionModel): ApiResultWrapper<Unit> {
        val transactionRequest = mapperTransactionToTransactionRequest.map(transactionModel)
        if (transactionRequest.amount > getWallet()!!) {
            return ApiResultWrapper.Error.NoMoneyError("Money Error")
        }
        return withdrawRepository.doTransaction(transactionRequest)
    }

    private suspend fun getWallet(): Double? {
        return userRepository.getLastWallet()?.amount
    }

}
