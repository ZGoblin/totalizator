package com.kvad.totalizator.beting.bethistory.model

import com.google.gson.annotations.SerializedName

data class BetHistoryPreview (
    @SerializedName("betsPreviewForUsers") val bets : List<RequestBetHistoryModel>
)
