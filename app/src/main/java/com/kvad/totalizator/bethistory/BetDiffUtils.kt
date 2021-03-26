package com.kvad.totalizator.bethistory

import androidx.recyclerview.widget.DiffUtil

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
