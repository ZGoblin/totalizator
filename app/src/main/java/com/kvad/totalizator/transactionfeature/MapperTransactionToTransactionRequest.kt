package com.kvad.totalizator.transactionfeature

import com.kvad.totalizator.data.requestmodels.TransactionRequest
import com.kvad.totalizator.tools.DEPOSIT_FLAG
import com.kvad.totalizator.tools.WITHDRAW_FLAG
import javax.inject.Inject

class MapperTransactionToTransactionRequest @Inject constructor(){

    fun map(transactionModel: TransactionModel) : TransactionRequest {
        return TransactionRequest(
            amount = transactionModel.amount,
            type = when(transactionModel.type){
                TransactionState.WITHDRAW -> WITHDRAW_FLAG
                TransactionState.DEPOSIT -> DEPOSIT_FLAG
            }
        )

    }
}