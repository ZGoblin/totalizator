package com.kvad.totalizator.bethistory

import android.content.res.TypedArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.R
import com.kvad.totalizator.databinding.BetHistoryItemBinding

class BetHistoryItemHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = BetHistoryItemBinding.bind(itemView)

    fun onBind(betHistoryDetailModel : BetHistoryDetailModel){
        binding.apply{
            tvTeamConformation.text = betHistoryDetailModel.teamConfrontation
            tvAmount.text = betHistoryDetailModel.amount.toString()
            tvBetTime.text = betHistoryDetailModel.betStartTime.toString()
            tvEventStartTime.text = betHistoryDetailModel.eventStartTime.toString()
        }
        when(betHistoryDetailModel.status){
            BetStatus.LOSE -> binding.tvStatus.setText(R.string.lose)
            BetStatus.WIN -> binding.tvStatus.setText(R.string.win)
        }
    }

}
