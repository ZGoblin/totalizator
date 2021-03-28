package com.kvad.totalizator.beting.bethistory.ui

import com.kvad.totalizator.beting.bethistory.model.BetHistoryDetailModel
import com.kvad.totalizator.beting.bethistory.model.RequestBetHistoryModel
import com.kvad.totalizator.tools.*
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_DATE
import javax.inject.Inject

class BetHistoryMapper @Inject constructor() {

    fun mapItem(requestBetHistoryModel: RequestBetHistoryModel) = BetHistoryDetailModel(
        id = requestBetHistoryModel.betId,
        teamConfrontation = requestBetHistoryModel.teamConfrontation,
        choice = when (requestBetHistoryModel.choice) {
            W1_SERVER_FLAG -> W1_BET_CHOICE
            W2_SERVER_FLAG -> W2_BET_CHOICE
            else -> DRAW_BET_CHOICE
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
        val timeParsed = ZonedDateTime.parse(time, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val month =
            if (timeParsed.monthValue.toString().length < 2) "0${timeParsed.monthValue}" else "${timeParsed.monthValue}"
        val day =
            if (timeParsed.dayOfMonth.toString().length < 2) "0${timeParsed.dayOfMonth}" else "${timeParsed.dayOfMonth}"
        return "$day.$month, ${timeParsed.hour}:${timeParsed.minute}"
    }
}

