package com.kvad.totalizator.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.databinding.OnBoardBinding
import com.kvad.totalizator.events.EventsFragment
import com.kvad.totalizator.onboard.viewPagerAdapter.BoardPagerAdapter
import com.kvad.totalizator.tools.sharedPrefTools.SharedPref
import javax.inject.Inject

class OnBoardFragment : Fragment(R.layout.on_board) {

    private var _binding: OnBoardBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var repository: BoardInfoRepository
    var counter: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OnBoardBinding.inflate(inflater, container, false)
        setupDi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTableLayout()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    fun swipeRight() {
        if (counter < 2) {
            binding.vpInfo.currentItem += 1
            counter = binding.vpInfo.currentItem
        } else {
            findNavController().navigate(R.id.events_fragment)
        }
    }

    fun swipeLeft() {
        binding.vpInfo.currentItem -= 1
        counter = binding.vpInfo.currentItem
    }

    private fun setupTableLayout() {
        binding.vpInfo.adapter = BoardPagerAdapter(this, repository.boardInfoList)
        TabLayoutMediator(binding.tlOnBoard, binding.vpInfo) { _, _ -> }.attach()
    }

    override fun onDestroyView() {
        binding.vpInfo.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
