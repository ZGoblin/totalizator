package com.kvad.totalizator.bethistory

import com.google.gson.annotations.SerializedName

data class BetHistoryPreview (
    @SerializedName("betsPreviewForUsers") val bets : List<RequestBetHistoryModel>
)