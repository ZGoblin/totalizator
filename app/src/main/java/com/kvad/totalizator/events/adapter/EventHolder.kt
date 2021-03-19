package com.kvad.totalizator.events.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.databinding.EventHolderBinding
import com.kvad.totalizator.data.models.Event

class EventHolder(view: View, private val onEventClick: (Event) -> Unit): RecyclerView.ViewHolder(view) {
    private lateinit var binding: EventHolderBinding

    fun onBind(event: Event) {
        binding = EventHolderBinding.bind(itemView)

        binding.root.setOnClickListener {
            onEventClick(event)
        }
    }
}
