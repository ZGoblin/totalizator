package com.kvad.totalizator.data

import com.kvad.totalizator.betfeature.BetRequest
import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.safeApiCall
import javax.inject.Inject

class BetRepository @Inject constructor(
    private val userService: UserService
) {
    suspend fun doBet(betRequest: BetRequest): ResultWrapper<Unit> {
        return safeApiCall {
            userService.doBet(betRequest)
        }
    }

}
