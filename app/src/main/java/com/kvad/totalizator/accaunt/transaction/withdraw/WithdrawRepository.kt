package com.kvad.totalizator.accaunt.transaction.withdraw

import com.kvad.totalizator.accaunt.data.UserService
import com.kvad.totalizator.accaunt.data.model.TransactionRequest
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import javax.inject.Inject

class WithdrawRepository @Inject constructor(
    private val userService: UserService
) {
    suspend fun doTransaction(transactionRequest: TransactionRequest): ApiResultWrapper<Unit> {
        return safeApiCall {
            userService.transaction(transactionRequest)
        }
    }
}
