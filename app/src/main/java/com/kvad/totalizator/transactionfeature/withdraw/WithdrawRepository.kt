package com.kvad.totalizator.transactionfeature.withdraw

import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.data.requestmodels.BetRequest
import com.kvad.totalizator.data.requestmodels.TransactionRequest
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
