package com.kvad.totalizator.event.detail

import com.kvad.totalizator.event.data.model.Event
import com.kvad.totalizator.event.detail.model.EventDetail
import javax.inject.Inject

class MapEventToDetailUiModel @Inject constructor() {

    fun map(event: Event): List<EventDetail> {

        val resultList = mutableListOf<EventDetail>()

        resultList.add(
            EventDetail.HeaderInfoUiModel(
                event.id,
                event.firstParticipant,
                event.secondParticipant,
                event.betPool,
                event.isLive
            )
        )

        if (!event.isLive) {
            resultList.add(
                EventDetail.ButtonsInfoUiModel(
                    event.id,
                    event.firstParticipant,
                    event.secondParticipant
                )
            )
        }

        val second = event.secondParticipant.characteristics
        val first = event.firstParticipant.characteristics

        val parameters = mutableListOf<EventDetail.CharacteristicUiModel>()
        first.forEach { characteristic ->
            parameters.add(
                EventDetail.CharacteristicUiModel(
                    characteristicName = characteristic.type,
                    firstPlayerValue = characteristic.value,
                    secondPlayerValue = ""
                )
            )
        }

        second.forEach { secondParams ->
            val params = parameters.find { it.characteristicName == secondParams.type }
            parameters.remove(params)
            parameters.add(
                EventDetail.CharacteristicUiModel(
                    characteristicName = secondParams.type,
                    firstPlayerValue = params?.firstPlayerValue ?: "",
                    secondPlayerValue = secondParams.value
                )
            )
        }

        parameters.forEach {
            resultList.add(it)
        }

        return resultList
    }
}
