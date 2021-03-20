package com.kvad.totalizator.detail.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kvad.totalizator.detail.model.EventDetail

class DetailEventDiffUtil : DiffUtil.ItemCallback<EventDetail>() {

    override fun areItemsTheSame(oldItem: EventDetail, newItem: EventDetail): Boolean {
        return when {
            oldItem is EventDetail.HeaderInfoUiModel && newItem is EventDetail.HeaderInfoUiModel -> {
                oldItem.id == newItem.id
            }
            oldItem is EventDetail.CharacteristicUiModel && newItem is EventDetail.CharacteristicUiModel -> {
                oldItem.characteristic == newItem.characteristic
            }
            else -> return false
        }
    }

    override fun areContentsTheSame(oldItem: EventDetail, newItem: EventDetail): Boolean {
        return when {
            oldItem is EventDetail.HeaderInfoUiModel && newItem is EventDetail.HeaderInfoUiModel -> {
                oldItem == newItem
            }
            oldItem is EventDetail.CharacteristicUiModel && newItem is EventDetail.CharacteristicUiModel -> {
                oldItem == newItem
            }
            else -> return false
        }
    }

}
