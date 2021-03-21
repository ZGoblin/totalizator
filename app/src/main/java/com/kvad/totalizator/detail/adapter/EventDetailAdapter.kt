package com.kvad.totalizator.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.R
import com.kvad.totalizator.detail.model.EventDetail

/*class EventDetailAdapter constructor(
        
) : ListAdapter<EventDetail, RecyclerView.ViewHolder>(DetailEventDiffUtil()) {

    enum class HolderType {
        HEADER,
        CHARACTERISTIC
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is EventDetail.HeaderInfoUiModel -> HolderType.HEADER
            is EventDetail.CharacteristicUiModel -> HolderType.CHARACTERISTIC
        }.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewType = HolderType.values()[viewType]

        val view = if (viewType == HolderType.HEADER) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.event_detail_header_view_holder, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.event_detail_player_characteristic, parent, false)
        }

        return if (viewType == HolderType.HEADER) {

        } else {

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

}*/
