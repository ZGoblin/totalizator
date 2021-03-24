package com.kvad.totalizator.chat.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kvad.totalizator.chat.model.UserMessageApiModel

class MessageDiffUtil : DiffUtil.ItemCallback<UserMessageApiModel>() {
    override fun areItemsTheSame(oldItem: UserMessageApiModel, newItem: UserMessageApiModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UserMessageApiModel, newItem: UserMessageApiModel) =
        oldItem == newItem
}
