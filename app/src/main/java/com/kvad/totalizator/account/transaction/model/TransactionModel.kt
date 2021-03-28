package com.kvad.totalizator.account.transaction.model

import com.kvad.totalizator.account.transaction.domain.TransactionType


data class TransactionModel (
    val amount : Double,
    val type : TransactionType
        )
