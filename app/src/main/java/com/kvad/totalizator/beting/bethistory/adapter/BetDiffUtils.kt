package com.kvad.totalizator.beting.bethistory.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kvad.totalizator.beting.bethistory.model.BetHistoryDetailModel

class BetDiffUtils : DiffUtil.ItemCallback<BetHistoryDetailModel>() {
    override fun areItemsTheSame(
        oldItem: BetHistoryDetailModel,
        newItem: BetHistoryDetailModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: BetHistoryDetailModel,
        newItem: BetHistoryDetailModel
    ): Boolean {
        return oldItem == newItem
    }

}
