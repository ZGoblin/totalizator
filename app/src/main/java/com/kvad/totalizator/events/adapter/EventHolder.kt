package com.kvad.totalizator.events.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.data.model.Event
import com.kvad.totalizator.databinding.EventHolderBinding

class EventHolder(
    view: View,
    private val onEventClick: (Event) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val binding = EventHolderBinding.bind(itemView)

    fun onBind(event: Event) {
        binding.evgEvent.apply {
            setFirstPlayerName(event.firstParticipant.name)
            setSecondPlayerName(event.secondParticipant.name)
            setBetScale(event.betPool)
            setFirstPlayerImg(event.firstParticipant.photoLink)
            setSecondPlayerImg(event.secondParticipant.photoLink)

            setOnClickListener {
                onEventClick(event)
            }
        }
    }
}
