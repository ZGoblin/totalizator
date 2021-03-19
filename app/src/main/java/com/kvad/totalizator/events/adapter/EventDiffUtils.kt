package com.kvad.totalizator.events.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kvad.totalizator.events.EventResponse

class EventDiffUtils: DiffUtil.ItemCallback<EventResponse>() {
    override fun areItemsTheSame(oldItem: EventResponse, newItem: EventResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EventResponse, newItem: EventResponse): Boolean {
        return oldItem == newItem
    }
}
