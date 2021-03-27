package com.kvad.totalizator.bethistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kvad.totalizator.R
import com.kvad.totalizator.bethistory.model.BetHistoryDetailModel

class BetHistoryAdapter : ListAdapter<BetHistoryDetailModel, BetHistoryItemHolder>(
    BetDiffUtils()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetHistoryItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bet_history_item, parent, false)
        return BetHistoryItemHolder(view)
    }

    override fun onBindViewHolder(holder: BetHistoryItemHolder, position: Int) {
        holder.onBind(getItem(position))
    }

}