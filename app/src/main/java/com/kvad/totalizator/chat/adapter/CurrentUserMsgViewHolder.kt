package com.kvad.totalizator.chat.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kvad.totalizator.R
import com.kvad.totalizator.chat.UserMessageUi
import com.kvad.totalizator.databinding.CurrentUserMessageHolderBinding

class CurrentUserMsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = CurrentUserMessageHolderBinding.bind(itemView)

    fun onBind(userMsg: UserMessageUi) {
        binding.apply {
            tvMessageBody.text = userMsg.text
            tvTime.text = userMsg.time

            Glide.with(itemView)
                .load(userMsg.image)
                .circleCrop()
                .error(R.drawable.player_image_not_found)
                .into(binding.ivAvatar)
        }
    }

}
