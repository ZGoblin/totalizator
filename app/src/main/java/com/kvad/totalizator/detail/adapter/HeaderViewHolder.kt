package com.kvad.totalizator.detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.databinding.EventDetailHeaderViewHolderBinding
import com.kvad.totalizator.detail.model.EventDetail
import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.shared.BetAmountForEachOutcome

class HeaderViewHolder(
    view: View,
    private val onBetButtonClick: (Int, Bet) -> Unit = { _, _ -> }
) : RecyclerView.ViewHolder(view) {

    private val binding = EventDetailHeaderViewHolderBinding.bind(itemView)

    private lateinit var eventInfo: EventDetail.HeaderInfoUiModel

    fun onBind(eventInfo: EventDetail.HeaderInfoUiModel) {

        this.eventInfo = eventInfo

        setupListeners()
        setupEventInfo()

    }

    private fun setupEventInfo() {
        binding.event.apply {

            eventInfo.betPool.apply {
                setBetScale(
                    BetAmountForEachOutcome(
                        firstPlayerWinBetAmount = firstAmount.toInt(),
                        secondPlayerWinBetAmount = secondAmount.toInt(),
                        draw = drawAmount.toInt()
                    )
                )
            }

            setFirstPlayerImg(eventInfo.participantDto1.photoLink)
            setSecondPlayerImg(eventInfo.participantDto2.photoLink)

            setFirstPlayerName(eventInfo.participantDto1.name)
            setSecondPlayerName(eventInfo.participantDto2.name)
        }
    }


    private fun setupListeners() {
        binding.btnDraw.setOnClickListener {
            onBetButtonClick.invoke(eventInfo.id, Bet.DRAW)
        }

        binding.btnFirstPlayerWin.setOnClickListener {
            onBetButtonClick.invoke(eventInfo.id, Bet.FIRST_PLAYER_WIN)
        }

        binding.btnSecondPlayerWin.setOnClickListener {
            onBetButtonClick.invoke(eventInfo.id, Bet.SECOND_PLAYER_WIN)
        }
    }
}
