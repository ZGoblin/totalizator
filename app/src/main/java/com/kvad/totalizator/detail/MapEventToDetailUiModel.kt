package com.kvad.totalizator.detail

import com.kvad.totalizator.data.model.Event
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
                    event.firstParticipant,
                    event.secondParticipant
                )
            )
        }

        event.firstParticipant.characteristics.forEach { firstPlayerCharacteristic ->
            event.secondParticipant.characteristics.forEach { secondPlayerCharacteristic ->
                if (firstPlayerCharacteristic.type == secondPlayerCharacteristic.type){
                    resultList.add(EventDetail.CharacteristicUiModel(
                        firstPlayerCharacteristic.type,
                        firstPlayerCharacteristic.value,
                        secondPlayerCharacteristic.value
                    ))
                }
            }
        }

        return resultList
    }
}
