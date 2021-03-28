package com.kvad.totalizator.accaunt.transaction.domain

import com.kvad.totalizator.accaunt.data.UserRepository
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.accaunt.data.MapperTransactionToTransactionRequest
import com.kvad.totalizator.accaunt.transaction.model.TransactionModel
import com.kvad.totalizator.accaunt.transaction.withdraw.WithdrawRepository
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
