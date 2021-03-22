package com.kvad.totalizator.onboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kvad.totalizator.R
import com.kvad.totalizator.databinding.BoardPageBinding
import com.kvad.totalizator.events.EventsFragment
import com.kvad.totalizator.onboard.model.BoardInfo
import com.kvad.totalizator.onboard.titleAdapter.BoardTitlesAdapter
import com.kvad.totalizator.tools.INFO_PAGER_KEY

class BoardPageFragment : Fragment(R.layout.board_page) {

    private lateinit var binding: BoardPageBinding
    private lateinit var info: BoardInfo
    private lateinit var titleAdapter: BoardTitlesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            info = it.getParcelable(INFO_PAGER_KEY) ?: BoardInfo(
                listOf(R.string.error_title),
                R.string.error_body
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BoardPageBinding.inflate(inflater, container, false)
        setupData()
        setupListeners()
        setupRecycler()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBackButton()
    }

    private fun hideBackButton() {
        if ((requireParentFragment() as OnBoardFragment).counter == 0) {
            binding.tvBack.visibility = View.GONE
        }
    }

    private fun setupData() {
        binding.apply {
            tvBody.text = getString(info.body)
        }
    }

    private fun setupRecycler() {
        val titles = info.titles.map { getString(it) }
        titleAdapter = BoardTitlesAdapter(titles)
        binding.rvTitle.apply {
            adapter = titleAdapter
            layoutManager = LinearLayoutManager(
                this.context,
                LinearLayoutManager.VERTICAL,
                false
            )

        }

    }


    private fun setupListeners() {
        binding.tvNext.setOnClickListener {
            (requireParentFragment() as OnBoardFragment).swipeRight()
            if ((requireParentFragment() as OnBoardFragment).counter == 2){
                requireActivity().onBackPressed()
            }
        }
        binding.tvBack.setOnClickListener {
            (requireParentFragment() as OnBoardFragment).swipeLeft()
        }

    }

    companion object {
        fun newInstance(info: BoardInfo) =
            BoardPageFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(INFO_PAGER_KEY, info)
                }
            }
    }

}
