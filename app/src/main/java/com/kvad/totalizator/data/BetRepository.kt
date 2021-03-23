package com.kvad.totalizator.data

import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.betfeature.BetRequest
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import retrofit2.Response
import javax.inject.Inject

class BetRepository @Inject constructor(
    //private val userService: UserService
) {

    suspend fun doBet(betRequest: BetRequest): ApiResultWrapper<Unit> {
        betRequest
        return safeApiCall {
            Response.success(Unit)
        }
    }

}
