package com.kvad.totalizator.data

import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.betfeature.BetRequest
import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import javax.inject.Inject

class BetRepository @Inject constructor(
    private val userService: UserService
) {

    suspend fun doBet(betRequest: BetRequest): ApiResultWrapper<Unit>  {
        return safeApiCall {
            userService.doBet(betRequest)
        }
    }

}
