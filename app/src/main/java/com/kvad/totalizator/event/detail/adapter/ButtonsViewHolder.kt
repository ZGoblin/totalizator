package com.kvad.totalizator.event.detail.detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.R
import com.kvad.totalizator.databinding.EventDetailButtonsHolderBinding
import com.kvad.totalizator.event.detail.detail.model.EventDetail
import com.kvad.totalizator.shared.Bet

class ButtonsViewHolder(
    view: View,
    private val onBetButtonClick: (Bet, String) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val binding = EventDetailButtonsHolderBinding.bind(itemView)

    fun onBind(buttonDetail: EventDetail.ButtonsInfoUiModel) {
        binding.apply {
            btnFirstPlayerWin.text =
                itemView.resources.getString(R.string.bet_on, buttonDetail.participant1.name)
            btnSecondPlayerWin.text =
                itemView.resources.getString(R.string.bet_on, buttonDetail.participant2.name)
        }

        setupListeners(buttonDetail)
    }

    private fun setupListeners(buttonDetail: EventDetail.ButtonsInfoUiModel) {
        binding.btnDraw.setOnClickListener {
            onBetButtonClick.invoke(Bet.DRAW, buttonDetail.id)
        }

        binding.btnFirstPlayerWin.setOnClickListener {
            onBetButtonClick.invoke(Bet.FIRST_PLAYER_WIN, buttonDetail.id)
        }

        binding.btnSecondPlayerWin.setOnClickListener {
            onBetButtonClick.invoke(Bet.SECOND_PLAYER_WIN, buttonDetail.id)
        }
    }
}
