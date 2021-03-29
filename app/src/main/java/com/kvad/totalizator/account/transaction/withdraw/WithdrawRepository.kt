package com.kvad.totalizator.account.transaction.withdraw

import com.kvad.totalizator.account.data.UserService
import com.kvad.totalizator.account.data.model.TransactionRequest
import com.kvad.totalizator.di.IoDispatcher
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WithdrawRepository @Inject constructor(
    private val userService: UserService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun doTransaction(transactionRequest: TransactionRequest): ApiResultWrapper<Unit> = withContext(dispatcher){
        return@withContext safeApiCall {
            userService.transaction(transactionRequest)
        }
    }
}
