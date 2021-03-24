package com.kvad.totalizator.transactionfeature.model

import com.kvad.totalizator.transactionfeature.TransactionState

data class TransactionModel (
    val amount : Double,
    val type : TransactionState
        )