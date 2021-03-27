package com.kvad.totalizator.bethistory.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.R
import com.kvad.totalizator.bethistory.model.BetHistoryDetailModel
import com.kvad.totalizator.databinding.BetHistoryItemBinding

class BetHistoryItemHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = BetHistoryItemBinding.bind(itemView)

    fun onBind(betHistoryDetailModel: BetHistoryDetailModel) {
        binding.apply {
            tvTeamConformation.text = betHistoryDetailModel.teamConfrontation
            tvAmount.text = itemView.resources.getString(R.string.stake,betHistoryDetailModel.amount.toString())
            tvBetTime.text = itemView.resources.getString(
                R.string.placed_bet,
                betHistoryDetailModel.betStartTime
            )
            tvEventStartTime.text = itemView.resources.getString(
                R.string.start_event,
                betHistoryDetailModel.eventStartTime
            )
            tvStatus.text = betHistoryDetailModel.status
            tvBetChoice.text = betHistoryDetailModel.choice
        }
    }

}
