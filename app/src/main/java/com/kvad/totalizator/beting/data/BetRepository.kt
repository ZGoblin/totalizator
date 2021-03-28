package com.kvad.totalizator.beting.data

import com.kvad.totalizator.beting.bethistory.model.BetHistoryDetailModel
import com.kvad.totalizator.beting.bethistory.ui.BetHistoryMapper
import com.kvad.totalizator.account.data.UserService
import com.kvad.totalizator.di.DefaultDispatcher
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.mapSuccess
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BetRepository @Inject constructor(
    private val userService: UserService,
    private val betHistoryMapper: BetHistoryMapper,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun getBetHistory(): ApiResultWrapper<List<BetHistoryDetailModel>> = withContext(dispatcher){
        return@withContext safeApiCall {
            userService.betHistory()
        }.mapSuccess { betHistoryMapper.map(it.bets.reversed()) }
    }

    suspend fun doBet(betRequest: BetRequest): ApiResultWrapper<Unit> = withContext(dispatcher){
        return@withContext safeApiCall {
            userService.doBet(betRequest)
        }
    }

}
