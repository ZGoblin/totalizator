package com.kvad.totalizator.data

import android.util.Log
import com.kvad.totalizator.betfeature.BetToServerModel
import com.kvad.totalizator.betfeature.MapperBetToString
import com.kvad.totalizator.tools.ResultWrapper
import com.kvad.totalizator.betfeature.BetRequest
import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.tools.safeApiCall
import retrofit2.Response
import javax.inject.Inject

class BetRepository @Inject constructor(
    private val userService: UserService
) {
    suspend fun doBet(betRequest: BetRequest): ResultWrapper<Unit> {
        return safeApiCall {
            Response.success(Unit)
        }
    }

}
