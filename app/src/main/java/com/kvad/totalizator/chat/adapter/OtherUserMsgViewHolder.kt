package com.kvad.totalizator.chat.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kvad.totalizator.R
import com.kvad.totalizator.chat.ui.UserMessageUi
import com.kvad.totalizator.databinding.OtherUserMessageHolderBinding

class OtherUserMsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val binding = OtherUserMessageHolderBinding.bind(itemView)

    fun onBind(userMsg: UserMessageUi) {
        binding.apply {
            tvMessageBody.text = userMsg.text
            tvTime.text = userMsg.time
            tvName.text = userMsg.userName

            Glide.with(itemView)
                .load(userMsg.image)
                .circleCrop()
                .error(R.drawable.player_image_not_found)
                .into(binding.ivAvatar)
        }
    }

}
