package com.kvad.totalizator.detail

import com.kvad.totalizator.data.model.Event
import com.kvad.totalizator.data.requestmodels.Characteristic
import com.kvad.totalizator.detail.model.EventDetail
import java.time.ZonedDateTime
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
//        val second = setOf(Characteristic("height", "180"), Characteristic("weight", "65"))
//        val first = setOf(Characteristic("height", "165"), Characteristic("weight", "80"), Characteristic("Age", "65"))

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

        second.forEach { playerCharacteristic ->
            parameters.find { characteristic ->
                characteristic.characteristicName == playerCharacteristic.type
            }?.let {
                parameters.add(
                    EventDetail.CharacteristicUiModel(
                        characteristicName = playerCharacteristic.type,
                        firstPlayerValue = it.firstPlayerValue,
                        secondPlayerValue = playerCharacteristic.value
                    )
                )
                parameters.remove(it)
                return@forEach
            }
            parameters.add(
                EventDetail.CharacteristicUiModel(
                    characteristicName = playerCharacteristic.type,
                    firstPlayerValue = "",
                    secondPlayerValue = playerCharacteristic.value
                )
            )
        }

        parameters.forEach {
            resultList.add(it)
        }

        return resultList
    }
}
