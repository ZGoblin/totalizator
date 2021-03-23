package com.kvad.totalizator.transactionfeature

data class TransactionBody (
    val amount : Double,
    val type : TransactionState
        )