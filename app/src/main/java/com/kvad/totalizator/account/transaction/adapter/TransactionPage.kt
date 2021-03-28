package com.kvad.totalizator.account.transaction.adapter

import androidx.annotation.StringRes
import com.kvad.totalizator.R

enum class TransactionPage(@StringRes val title: Int) {
    DEPOSIT_PAGE(R.string.deposit),
    WITHDRAW_PAGE(R.string.withdraw)
}
