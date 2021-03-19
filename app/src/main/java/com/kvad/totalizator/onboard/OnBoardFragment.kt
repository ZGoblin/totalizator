package com.kvad.totalizator.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.databinding.OnBoardBinding
import com.kvad.totalizator.onboard.viewPagerAdapter.BoardPagerAdapter
import javax.inject.Inject

class OnBoardFragment : Fragment(R.layout.on_board) {

    private lateinit var binding: OnBoardBinding

    @Inject
    lateinit var repository: BoardInfoRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTableLayout()
        setupDi()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    fun swipeRight() {
        binding.vpInfo.currentItem += 1
    }

    fun swipeLeft() {
        binding.vpInfo.currentItem -= 1
    }

    private fun setupTableLayout() {
        binding.vpInfo.adapter = BoardPagerAdapter(this, repository.boardInfoList)
        TabLayoutMediator(binding.tlOnBoard, binding.vpInfo) { _, _ -> }.attach()
    }

}
