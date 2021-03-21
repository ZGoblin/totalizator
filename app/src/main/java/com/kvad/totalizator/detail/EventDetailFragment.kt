package com.kvad.totalizator.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kvad.totalizator.App
import com.kvad.totalizator.betfeature.BetDialogFragment
import com.kvad.totalizator.betfeature.model.ChoiceModel
import com.kvad.totalizator.databinding.EventDetailFragmentBinding
import com.kvad.totalizator.detail.adapter.EventDetailAdapter
import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.tools.State
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
            //todo clean up
            Toast.makeText(context, eventId, Toast.LENGTH_SHORT).show()
        }

        setupDi()
        setupRecyclerView()
        setupViewModelObserver()

        viewModel.uploadData(eventId)
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

    private fun setupViewModelObserver() {
        viewModel.commentLiveData.observe(viewLifecycleOwner) {
            updateScreenInfo(it)
        }
    }

    private fun updateScreenInfo(state: eventDetailState) {
        when (state) {
            is State.Content -> eventDetailAdapter.submitList(state.data)
            is State.Error -> binding.rvEventDetailInfo.visibility = View.GONE
            is State.Loading -> Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
        }
    }

    private fun onBtnBetClick(bet: Bet) {
        Toast.makeText(context, "$eventId with bet $bet", Toast.LENGTH_SHORT).show()
        val fakeModelFromEvent = ChoiceModel(
            eventId, bet,
            "First player",
            "Second player"
        )
        // TODO 21.03.2021 start bottom sheet dialog
        childFragmentManager.beginTransaction()
            .add(BetDialogFragment.newInstance(fakeModelFromEvent), "TAG")
            .commitAllowingStateLoss()
    }
}
