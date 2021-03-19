package com.kvad.totalizator.onboard.viewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kvad.totalizator.onboard.BoardPageFragment
import com.kvad.totalizator.onboard.model.BoardInfo

class BoardPagerAdapter(
    fragment: Fragment,
    private val items: List<BoardInfo>,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return BoardPageFragment.newInstance(items[position])
    }


}
