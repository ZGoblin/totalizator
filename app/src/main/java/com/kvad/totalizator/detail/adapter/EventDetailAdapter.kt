package com.kvad.totalizator.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.R
import com.kvad.totalizator.detail.model.EventDetail
import com.kvad.totalizator.shared.Bet

class EventDetailAdapter constructor(
    private val onBetButtonClick: (Bet) -> Unit = {  _ -> }
) : ListAdapter<EventDetail, RecyclerView.ViewHolder>(DetailEventDiffUtil()) {

    enum class HolderType {
        HEADER,
        BUTTONS,
        CHARACTERISTIC
    }

    override fun getItemViewType(position: Int): Int  {
        return when (getItem(position)) {
            is EventDetail.HeaderInfoUiModel -> HolderType.HEADER
            is EventDetail.CharacteristicUiModel -> HolderType.CHARACTERISTIC
            is EventDetail.ButtonsInfoUiModel -> HolderType.BUTTONS
        }.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewTypeEnum = HolderType.values()[viewType]

        val layout = when (viewTypeEnum) {
            HolderType.HEADER -> R.layout.event_detail_header_view_holder
            HolderType.CHARACTERISTIC -> R.layout.event_detail_player_characteristic
            HolderType.BUTTONS -> R.layout.event_detail_buttons_holder
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return when (viewTypeEnum) {
            HolderType.HEADER -> HeaderViewHolder(view)
            HolderType.CHARACTERISTIC -> CharacteristicViewHolder(view)
            HolderType.BUTTONS -> ButtonsViewHolder(view, onBetButtonClick)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.onBind(getItem(position) as EventDetail.HeaderInfoUiModel)
            is CharacteristicViewHolder -> holder.onBind(getItem(position) as EventDetail.CharacteristicUiModel)
            is ButtonsViewHolder -> holder.onBind(getItem(position) as EventDetail.ButtonsInfoUiModel)
        }
    }
}
