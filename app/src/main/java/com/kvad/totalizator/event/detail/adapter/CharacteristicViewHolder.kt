package com.kvad.totalizator.event.detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.databinding.EventDetailPlayerCharacteristicBinding
import com.kvad.totalizator.event.detail.model.EventDetail

class CharacteristicViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = EventDetailPlayerCharacteristicBinding.bind(itemView)

    fun onBind(characteristic: EventDetail.CharacteristicUiModel) {
        binding.apply {
            tvCharacteristicName.text = characteristic.characteristicName
            tvFirstPlayerValue.text = characteristic.firstPlayerValue
            tvSecondPlayerValue.text = characteristic.secondPlayerValue
        }
    }
}
