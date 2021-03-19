package com.kvad.totalizator.events.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.databinding.EventHolderBinding
import com.kvad.totalizator.data.models.Event

class EventHolder(view: View, private val onEventClick: (Event) -> Unit): RecyclerView.ViewHolder(view) {
    private lateinit var binding: EventHolderBinding

    fun onBind(event: Event) {
        binding = EventHolderBinding.bind(itemView)

        binding.evgEvent.apply {
            setFirstPlayerName(event.participantdto1.name)
            setSecondPlayerName(event.participantdto2.name)
            setFirstPlayerBet(event.betPool.firstAmount.toInt())
            setSecondPlayerBet(event.betPool.secondAmount.toInt())
            setDrawBet(0)
        }

        binding.root.setOnClickListener {
            onEventClick(event)
        }
    }
}
