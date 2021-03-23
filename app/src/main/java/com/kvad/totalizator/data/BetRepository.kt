package com.kvad.totalizator.data

import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.betfeature.BetRequest
import com.kvad.totalizator.data.api.UserService
import com.kvad.totalizator.data.models.Token
import com.kvad.totalizator.data.models.Wallet
import com.kvad.totalizator.tools.REQUEST_DELAY
import com.kvad.totalizator.tools.safeapicall.safeApiCall
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.reflect.TypeVariable
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
