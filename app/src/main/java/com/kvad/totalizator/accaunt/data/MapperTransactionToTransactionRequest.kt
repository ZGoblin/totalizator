package com.kvad.totalizator.accaunt.data

import com.kvad.totalizator.accaunt.data.model.TransactionRequest
import com.kvad.totalizator.tools.DEPOSIT_FLAG
import com.kvad.totalizator.tools.WITHDRAW_FLAG
import com.kvad.totalizator.accaunt.transaction.model.TransactionModel
import com.kvad.totalizator.accaunt.transaction.domain.TransactionType
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
