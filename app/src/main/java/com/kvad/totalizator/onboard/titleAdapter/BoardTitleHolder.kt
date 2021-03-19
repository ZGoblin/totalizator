package com.kvad.totalizator.onboard.titleAdapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.databinding.BoardTitleItemBinding

class BoardTitleHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    private val binding = BoardTitleItemBinding.bind(itemView)
    fun onBind(title : String){
        binding.tvTitleItem.text = title
    }

}
