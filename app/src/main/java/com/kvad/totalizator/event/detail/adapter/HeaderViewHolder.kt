package com.kvad.totalizator.event.detail.detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.databinding.EventDetailHeaderViewHolderBinding
import com.kvad.totalizator.event.detail.detail.model.EventDetail

class HeaderViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    private val binding = EventDetailHeaderViewHolderBinding.bind(itemView)

    private lateinit var eventInfo: EventDetail.HeaderInfoUiModel

    fun onBind(eventInfo: EventDetail.HeaderInfoUiModel) {

        this.eventInfo = eventInfo
        setupEventInfo()
    }

    private fun setupEventInfo() {
        binding.event.apply {

            eventInfo.betPool.apply {
                setBetScale(eventInfo.betPool)
            }

            setFirstPlayerImg(eventInfo.participant1.photoLink)
            setSecondPlayerImg(eventInfo.participant2.photoLink)

            setFirstPlayerName(eventInfo.participant1.name)
            setSecondPlayerName(eventInfo.participant2.name)

            hideLive(!eventInfo.isLive)
        }
    }
}
