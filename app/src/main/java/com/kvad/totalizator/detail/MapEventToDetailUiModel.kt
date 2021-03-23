package com.kvad.totalizator.detail

import com.kvad.totalizator.data.model.BetPool
import com.kvad.totalizator.data.model.Event
import com.kvad.totalizator.detail.model.EventDetail
import javax.inject.Inject

@Suppress("MagicNumber")
class MapEventToDetailUiModel @Inject constructor() {

    fun map(event: Event): List<EventDetail> {

        val resultList = mutableListOf<EventDetail>()

        resultList.add(
            EventDetail.HeaderInfoUiModel(
                event.id,
                event.sportName,
                event.participant1,
                event.participant2,
                BetPool(event.amountW1, event.amountW2, event.amountX)
            )
        )

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
