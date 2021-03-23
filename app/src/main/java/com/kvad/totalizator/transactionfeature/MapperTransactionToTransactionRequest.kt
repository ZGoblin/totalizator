package com.kvad.totalizator.transactionfeature

import com.kvad.totalizator.data.models.TransactionRequest
import javax.inject.Inject

class MapperTransactionToTransactionRequest @Inject constructor(){

    fun map(transactionModel: TransactionModel) : TransactionRequest{
        return TransactionRequest(
            amount = transactionModel.amount,
            type = when(transactionModel.type){
                TransactionState.WITHDRAW -> "withdraw"
                TransactionState.DEPOSIT -> "deposit"
            }
        )

    }
}