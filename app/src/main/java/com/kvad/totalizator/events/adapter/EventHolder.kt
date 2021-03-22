package com.kvad.totalizator.events.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.databinding.EventHolderBinding
import com.kvad.totalizator.shared.BetAmountForEachOutcome

class EventHolder(view: View, private val onEventClick: (Event) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val binding = EventHolderBinding.bind(itemView)

    fun onBind(event: Event) {
        binding.evgEvent.apply {
            setFirstPlayerName(event.participantDto1.name)
            setSecondPlayerName(event.participantDto2.name)
            setBetScale(
                BetAmountForEachOutcome(
                    firstPlayerWinBetAmount = event.betPool.firstAmount.toInt(),
                    secondPlayerWinBetAmount = event.betPool.secondAmount.toInt(),
                    draw = event.betPool.drawAmount.toInt()
                )
            )
            setFirstPlayerImg(event.participantDto1.photoLink)
            setSecondPlayerImg(event.participantDto2.photoLink)
        }

        binding.root.setOnClickListener {
            onEventClick(event)
        }
    }
}
