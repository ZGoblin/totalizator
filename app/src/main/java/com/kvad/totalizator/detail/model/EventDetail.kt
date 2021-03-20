package com.kvad.totalizator.detail.model

import com.kvad.totalizator.data.models.BetPool
import com.kvad.totalizator.data.models.Characteristic
import com.kvad.totalizator.data.models.ParticipantDTO

sealed class EventDetail {
    data class CharacteristicUiModel(val characteristic: Characteristic) : EventDetail()

    data class HeaderInfoUiModel(
        val id: Int,
        val sport: String,
        val participantDto1: ParticipantDTO,
        val participantDto2: ParticipantDTO,
        val betPool: BetPool
    ) : EventDetail()
}
