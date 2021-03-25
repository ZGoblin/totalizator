
package com.kvad.totalizator.transactionfeature.domain

import android.util.Log
import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.data.requestmodels.TransactionRequest
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.transactionfeature.TransactionState
import com.kvad.totalizator.transactionfeature.TransactionType
import com.kvad.totalizator.transactionfeature.data.MapperTransactionToTransactionRequest
import com.kvad.totalizator.transactionfeature.model.TransactionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val mapperTransactionToTransactionRequest: MapperTransactionToTransactionRequest
) {
    suspend fun deposit(transactionModel: TransactionModel): ApiResultWrapper<Unit> {
        val transactionRequest = mapperTransactionToTransactionRequest.map(transactionModel)
        return userRepository.doTransaction(transactionRequest)
    }

}
