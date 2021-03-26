package com.kvad.totalizator.transactionfeature.data

import com.kvad.totalizator.data.requestmodels.TransactionRequest
import com.kvad.totalizator.tools.DEPOSIT_FLAG
import com.kvad.totalizator.tools.WITHDRAW_FLAG
import com.kvad.totalizator.transactionfeature.model.TransactionModel
import com.kvad.totalizator.transactionfeature.domain.TransactionType
import javax.inject.Inject

class MapperTransactionToTransactionRequest @Inject constructor(){

    fun map(transactionModel: TransactionModel) : TransactionRequest {
        return TransactionRequest(
            amount = transactionModel.amount,
            type = when(transactionModel.type){
                TransactionType.WITHDRAW -> WITHDRAW_FLAG
                TransactionType.DEPOSIT -> DEPOSIT_FLAG
            }
        )

    }
}

