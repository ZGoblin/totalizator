package com.kvad.totalizator.detail

import com.kvad.totalizator.data.models.BetPool
import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.detail.model.EventDetail
import javax.inject.Inject

class MapEventToDetailUiModel @Inject constructor() {

    fun map(event: Event): List<EventDetail> {

        val resultList = mutableListOf<EventDetail>()

        resultList.add(
            EventDetail.HeaderInfoUiModel(
                event.id,
                event.sportName,
                event.participant1,
                event.participant2,
                BetPool(1000f, 1000f, 1000f)
            )
        )

        event.participant1.characteristics.forEach { firstPlayerCharacteristic ->
            event.participant2.characteristics.forEach { secondPlayerCharacteristic ->
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
