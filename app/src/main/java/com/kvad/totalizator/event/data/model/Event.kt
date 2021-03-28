package com.kvad.totalizator.event.data.model

import com.kvad.totalizator.event.data.requestmodels.Participant
import java.time.ZonedDateTime

data class Event(
    val id: String,
    val firstParticipant: Participant,
    val secondParticipant: Participant,
    val startTime: ZonedDateTime,
    val isEnded: Boolean,
    val isLive: Boolean,
    val margin: Float,
    val betPool: BetPool
)
