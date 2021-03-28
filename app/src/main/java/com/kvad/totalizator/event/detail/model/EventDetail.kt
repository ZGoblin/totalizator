package com.kvad.totalizator.event.detail.detail.model

import com.kvad.totalizator.event.data.model.BetPool
import com.kvad.totalizator.event.data.requestmodels.Participant

sealed class EventDetail {
    data class CharacteristicUiModel(
        val characteristicName: String,
        val firstPlayerValue: String,
        val secondPlayerValue: String
    ) : EventDetail()

    data class HeaderInfoUiModel(
        val id: String,
        val participant1: Participant,
        val participant2: Participant,
        val betPool: BetPool,
        val isLive: Boolean
    ) : EventDetail()

    data class ButtonsInfoUiModel(
        val id: String,
        val participant1: Participant,
        val participant2: Participant
    ) : EventDetail()
}
