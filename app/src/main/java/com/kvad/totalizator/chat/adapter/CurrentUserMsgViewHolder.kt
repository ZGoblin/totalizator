package com.kvad.totalizator.chat.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.chat.UserMessageUi
import com.kvad.totalizator.databinding.CurrentUserMessageHolderBinding

class CurrentUserMsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = CurrentUserMessageHolderBinding.bind(itemView)

    fun onBind(userMsg: UserMessageUi) {
        binding.apply {
            tvMessageBody.text = userMsg.text
            tvTime.text = userMsg.time
        }
    }

}
