package com.kvad.totalizator.onboard.titleAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kvad.totalizator.R
import com.kvad.totalizator.onboard.titleAdapter.BoardTitleHolder

class BoardTitlesAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<BoardTitleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardTitleHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.board_title_item, parent, false)
        return BoardTitleHolder(view)
    }

    override fun onBindViewHolder(holder: BoardTitleHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
