package com.kvad.totalizator.accaunt.transaction.model

import com.kvad.totalizator.accaunt.transaction.domain.TransactionType


data class TransactionModel (
    val amount : Double,
    val type : TransactionType
        )
