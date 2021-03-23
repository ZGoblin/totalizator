package com.kvad.totalizator.detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.R
import com.kvad.totalizator.databinding.EventDetailButtonsHolderBinding
import com.kvad.totalizator.detail.model.EventDetail
import com.kvad.totalizator.shared.Bet

class ButtonsViewHolder(
    view: View,
    private val onBetButtonClick: (Bet) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val binding = EventDetailButtonsHolderBinding.bind(itemView)

    fun onBind(buttonDetail: EventDetail.ButtonsInfoUiModel) {
        binding.apply {
            btnFirstPlayerWin.text =
                itemView.resources.getString(R.string.bet_on, buttonDetail.participant1.name)
            btnSecondPlayerWin.text =
                itemView.resources.getString(R.string.bet_on, buttonDetail.participant2.name)
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnDraw.setOnClickListener {
            onBetButtonClick.invoke(Bet.DRAW)
        }

        binding.btnFirstPlayerWin.setOnClickListener {
            onBetButtonClick.invoke(Bet.FIRST_PLAYER_WIN)
        }

        binding.btnSecondPlayerWin.setOnClickListener {
            onBetButtonClick.invoke(Bet.SECOND_PLAYER_WIN)
        }
    }
}
