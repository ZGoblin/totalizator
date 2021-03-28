package com.kvad.totalizator.transactionfeature.model

import com.kvad.totalizator.transactionfeature.domain.TransactionType


data class TransactionModel (
    val amount : Double,
    val type : TransactionType
        )
