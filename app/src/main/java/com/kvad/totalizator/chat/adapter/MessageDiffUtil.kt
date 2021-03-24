package com.kvad.totalizator.chat.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kvad.totalizator.chat.model.UserMessage

class MessageDiffUtil : DiffUtil.ItemCallback<UserMessage>() {
    override fun areItemsTheSame(oldItem: UserMessage, newItem: UserMessage) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UserMessage, newItem: UserMessage) =
        oldItem == newItem
}
