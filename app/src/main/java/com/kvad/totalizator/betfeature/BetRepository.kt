package com.kvad.totalizator.betfeature

import com.kvad.totalizator.bethistory.BetHistoryDetailModel
import com.kvad.totalizator.bethistory.BetHistoryMapper
import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.data.requestmodels.BetRequest
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.mapSuccess
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BetRepository @Inject constructor(
    private val userService: UserService,
    private val betHistoryMapper: BetHistoryMapper
) {

    private val _history : MutableSharedFlow<ApiResultWrapper<List<BetHistoryDetailModel>>> = MutableSharedFlow(replay = 1)
    val history : SharedFlow<ApiResultWrapper<List<BetHistoryDetailModel>>> = _history


    init{
        GlobalScope.launch{
            flow{
               val betHistory = safeApiCall(userService::betHistory).mapSuccess {
                   betHistoryMapper.map(it.bets)
               }
                emit(betHistory)
            }.collect {
                _history.emit(it)
            }
        }
    }

    suspend fun doBet(betRequest: BetRequest): ApiResultWrapper<Unit> {
        return safeApiCall {
            userService.doBet(betRequest)
        }
    }

}
