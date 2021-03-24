package com.kvad.totalizator.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.R
import com.kvad.totalizator.chat.UserMessageUi

class ChatRecyclerViewAdapter :
    ListAdapter<UserMessageUi, RecyclerView.ViewHolder>(MessageDiffUtil()) {

    enum class MessageViewType { CURRENT_USER_MSG, OTHER_USER_MSG }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isCurrentUserMessage) {
            MessageViewType.CURRENT_USER_MSG.ordinal
        } else MessageViewType.OTHER_USER_MSG.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewTypeEnum = MessageViewType.values()[viewType]

        //todo
        val layout = when (viewTypeEnum) {
            MessageViewType.CURRENT_USER_MSG -> R.layout.current_user_message_holder
            MessageViewType.OTHER_USER_MSG -> R.layout.current_user_message_holder
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return when (viewTypeEnum) {
            MessageViewType.OTHER_USER_MSG -> OtherUserMsgViewHolder(view)
            MessageViewType.CURRENT_USER_MSG -> CurrentUserMsgViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CurrentUserMsgViewHolder -> holder.onBind(getItem(position))
            is OtherUserMsgViewHolder -> holder.onBind(getItem(position))
        }
    }

}
