package com.kvad.totalizator.detail

import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.detail.model.EventDetail
import javax.inject.Inject

class MapEventToDetailUiModel @Inject constructor() {

    fun map(event: Event): List<EventDetail> {

        val resultList = mutableListOf<EventDetail>()

        resultList.add(
            EventDetail.HeaderInfoUiModel(
                event.id,
                event.sport,
                event.participantDto1,
                event.participantDto2,
                event.betPool
            )
        )

        event.participantDto1.characteristics.forEach { firstPlayerCharacteristic ->
            event.participantDto2.characteristics.forEach { secondPlayerCharacteristic ->
                if (firstPlayerCharacteristic.type == secondPlayerCharacteristic.type){
                    resultList.add(EventDetail.CharacteristicUiModel(
                        firstPlayerCharacteristic.type,
                        firstPlayerCharacteristic.playerValue,
                        secondPlayerCharacteristic.playerValue
                    ))
                }
            }
        }

        return resultList
    }
}
