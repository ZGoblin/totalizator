package com.kvad.totalizator.bethistory

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.databinding.BetHistoryItemBinding

class BetHistoryItemHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = BetHistoryItemBinding.bind(itemView)

    fun onBind(betHistoryDetailModel : BetHistoryDetailModel){
        binding.apply{
            tvTeamConformation.text = betHistoryDetailModel.teamConfrontation
            tvAmount.text = betHistoryDetailModel.amount.toString()
            tvBetTime.text = betHistoryDetailModel.betStartTime
            tvEventStartTime.text = betHistoryDetailModel.eventStartTime
            tvStatus.text = betHistoryDetailModel.status.toString()
        }
    }

}
