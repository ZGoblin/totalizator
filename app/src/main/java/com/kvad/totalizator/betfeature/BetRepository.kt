package com.kvad.totalizator.betfeature

import com.kvad.totalizator.bethistory.model.BetHistoryDetailModel
import com.kvad.totalizator.bethistory.ui.BetHistoryMapper
import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.data.requestmodels.BetRequest
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.mapSuccess
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BetRepository @Inject constructor(
    private val userService: UserService,
    private val betHistoryMapper: BetHistoryMapper
) {

    suspend fun getBetHistory(): ApiResultWrapper<List<BetHistoryDetailModel>> {
        return safeApiCall {
            userService.betHistory()
        }.mapSuccess { betHistoryMapper.map(it.bets.reversed()) }
    }

    suspend fun doBet(betRequest: BetRequest): ApiResultWrapper<Unit> {
        return safeApiCall {
            userService.doBet(betRequest)
        }
    }

}
