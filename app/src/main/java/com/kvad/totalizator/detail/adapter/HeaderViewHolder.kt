package com.kvad.totalizator.detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.R
import com.kvad.totalizator.data.model.BetPool
import com.kvad.totalizator.databinding.EventDetailHeaderViewHolderBinding
import com.kvad.totalizator.detail.model.EventDetail
import com.kvad.totalizator.shared.Bet

class HeaderViewHolder(
    view: View,
    private val onBetButtonClick: (Bet) -> Unit = { _ -> }
) : RecyclerView.ViewHolder(view) {

    private val binding = EventDetailHeaderViewHolderBinding.bind(itemView)

    private lateinit var eventInfo: EventDetail.HeaderInfoUiModel

    fun onBind(eventInfo: EventDetail.HeaderInfoUiModel) {

        this.eventInfo = eventInfo

        setupListeners()
        setupEventInfo()

    }

    // TODO 23.03.2021
    //  fix betpool
    private fun setupEventInfo() {
        binding.event.apply {

            eventInfo.betPool.apply {
                setBetScale(
                    BetPool(
                        firstPlayerBetAmount = firstPlayerBetAmount,
                        secondPlayerBetAmount = secondPlayerBetAmount,
                        drawBetAmount = drawBetAmount
                    )

                )
            }

            setFirstPlayerImg(eventInfo.participant1.photoLink)
            setSecondPlayerImg(eventInfo.participant2.photoLink)

            setFirstPlayerName(eventInfo.participant1.name)
            setSecondPlayerName(eventInfo.participant2.name)

        }


        binding.apply {
            btnFirstPlayerWin.text =
                itemView.resources.getString(R.string.bet_on, eventInfo.participant1.name)
            btnSecondPlayerWin.text =
                itemView.resources.getString(R.string.bet_on, eventInfo.participant2.name)
        }
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
