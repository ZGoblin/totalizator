package com.kvad.totalizator.events.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.databinding.EventHolderBinding
import com.kvad.totalizator.events.EventResponse

class EventHolder(view: View, private val onEventClick: (EventResponse) -> Unit): RecyclerView.ViewHolder(view) {
    private lateinit var binding: EventHolderBinding

    fun onBind(event: EventResponse) {
        binding = EventHolderBinding.bind(itemView)

        binding.root.setOnClickListener {
            onEventClick(event)
        }
    }
}
