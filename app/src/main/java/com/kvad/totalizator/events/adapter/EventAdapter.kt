package com.kvad.totalizator.events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kvad.totalizator.R
import com.kvad.totalizator.events.EventResponse

class EventAdapter(
    private val onEventClick: (EventResponse) -> Unit = {}
) : ListAdapter<EventResponse, EventHolder>(EventDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_holder, parent, false)
        return EventHolder(view, onEventClick)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.onBind(getItem(position))
    }

}
