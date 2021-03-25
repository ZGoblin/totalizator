package com.kvad.totalizator.betfeature

import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper

sealed class BetStateError {
    data class ServerError(
        val data : ApiResultWrapper<Unit>
    )
    object BetAmountMoreThenWalletAmount
}