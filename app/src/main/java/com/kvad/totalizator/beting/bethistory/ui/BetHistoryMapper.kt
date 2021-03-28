package com.kvad.totalizator.beting.bethistory.ui

import com.kvad.totalizator.beting.bethistory.model.BetHistoryDetailModel
import com.kvad.totalizator.beting.bethistory.model.RequestBetHistoryModel
import com.kvad.totalizator.tools.W1_SERVER_FLAG
import com.kvad.totalizator.tools.W2_SERVER_FLAG
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_DATE
import javax.inject.Inject

class BetHistoryMapper @Inject constructor() {

    fun mapItem(requestBetHistoryModel: RequestBetHistoryModel) = BetHistoryDetailModel(
        id = requestBetHistoryModel.betId,
        teamConfrontation = requestBetHistoryModel.teamConfrontation,
        choice = when (requestBetHistoryModel.choice) {
            W1_SERVER_FLAG -> "1"
            W2_SERVER_FLAG -> "2"
            else -> "X"
        },
        eventStartTime = parseZonedDateTime(requestBetHistoryModel.eventStartTime),
        betStartTime = parseZonedDateTime(requestBetHistoryModel.betTime),
        amount = requestBetHistoryModel.amount,
        status = requestBetHistoryModel.status,
    )

    fun map(betHistoryPreview: List<RequestBetHistoryModel>): List<BetHistoryDetailModel> {
        return betHistoryPreview.map { mapItem(it) }
    }

    private fun parseZonedDateTime(time: String): String {
        val zonedDateTime = ZonedDateTime.parse(time, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

        val month =
            if (zonedDateTime.monthValue.toString().length < 2) "0${zonedDateTime.monthValue}" else "${zonedDateTime.monthValue}"

        return "${zonedDateTime.dayOfMonth}.$month, ${zonedDateTime.hour}:${zonedDateTime.minute}"
    }
}

