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
            setFirstPlayerName(event.participant1.name)
            setSecondPlayerName(event.participant2.name)
            setBetScale(
                BetAmountForEachOutcome(
                    firstPlayerWinBetAmount = event.amountW1.toInt(),
                    secondPlayerWinBetAmount = event.amountW2.toInt(),
                    draw = event.amountX.toInt()
                )
            )
            setFirstPlayerImg(event.participant1.photoLink)
            setSecondPlayerImg(event.participant2.photoLink)

            setOnClickListener {
                onEventClick(event)
            }
        }
    }
}
