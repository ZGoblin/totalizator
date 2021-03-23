package com.kvad.totalizator.transactionfeature

data class TransactionModel (
    val amount : Double,
    val type : TransactionState
        )