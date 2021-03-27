package com.kvad.totalizator.bethistory.model

import com.google.gson.annotations.SerializedName
import com.kvad.totalizator.bethistory.model.RequestBetHistoryModel

data class BetHistoryPreview (
    @SerializedName("betsPreviewForUsers") val bets : List<RequestBetHistoryModel>
)
