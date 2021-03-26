package com.kvad.totalizator.transactionfeature.adapter

import androidx.annotation.StringRes
import com.kvad.totalizator.R

enum class TransactionPage(@StringRes val title: Int) {
    WITHDRAW_PAGE(R.string.withdraw),
    DEPOSIT_PAGE(R.string.deposit)
}
