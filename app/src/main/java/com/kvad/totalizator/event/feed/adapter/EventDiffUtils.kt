package com.kvad.totalizator.event.feed.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kvad.totalizator.event.data.model.Event

class EventDiffUtils: DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }
}
