package com.kvad.totalizator.transactionfeature

import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import javax.inject.Inject

class TransactionUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val mapperTransactionToTransactionRequest: MapperTransactionToTransactionRequest
){
    suspend fun deposit(transactionModel: TransactionModel) : ApiResultWrapper<Unit>{
        val transactionRequest = mapperTransactionToTransactionRequest.map(transactionModel)
        return userRepository.doTransaction(transactionRequest)
    }
}