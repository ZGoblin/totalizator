package com.kvad.totalizator.transactionfeature

import com.kvad.totalizator.data.models.TransactionRequest
import javax.inject.Inject

class MapperTransactionToTransactionRequest @Inject constructor(){

    fun map(transactionBody: TransactionBody) : TransactionRequest{
        return TransactionRequest(
            amount = transactionBody.amount,
            type = when(transactionBody.type){
                TransactionState.WITHDRAW -> "withdraw"
                TransactionState.DEPOSIT -> "deposit"
            }
        )

    }
}