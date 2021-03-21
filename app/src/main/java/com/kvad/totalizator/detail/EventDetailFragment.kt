package com.kvad.totalizator.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kvad.totalizator.App
import com.kvad.totalizator.databinding.EventDetailFragmentBinding
import com.kvad.totalizator.detail.adapter.EventDetailAdapter
import com.kvad.totalizator.shared.Bet
import javax.inject.Inject

class EventDetailFragment : Fragment() {

    @Inject
    lateinit var viewModel: EventDetailViewModel
    private lateinit var binding: EventDetailFragmentBinding

    private var eventId: String = ""
    private val eventDetailAdapter = EventDetailAdapter(::onBtnBetClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EventDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            eventId = EventDetailFragmentArgs.fromBundle(it).eventId
            Toast.makeText(context, eventId, Toast.LENGTH_SHORT).show()
        }

        setupDi()
        setupRecyclerView()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupRecyclerView() {
        binding.rvEventDetailInfo.apply {
            adapter = eventDetailAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun onBtnBetClick(bet: Bet) {
        Toast.makeText(context, "$eventId with bet $bet", Toast.LENGTH_SHORT).show()
        // TODO 21.03.2021 start bottom sheet dialog
    }
}
