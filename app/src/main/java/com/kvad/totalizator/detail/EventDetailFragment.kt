package com.kvad.totalizator.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.kvad.totalizator.App
import com.kvad.totalizator.betfeature.BetDialogFragment
import com.kvad.totalizator.betfeature.model.ChoiceModel
import com.kvad.totalizator.databinding.EventDetailFragmentBinding
import com.kvad.totalizator.detail.adapter.EventDetailAdapter
import com.kvad.totalizator.detail.model.EventDetail
import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.StateVisibilityController
import javax.inject.Inject

class EventDetailFragment : Fragment() {

    @Inject
    lateinit var viewModel: EventDetailViewModel
    private lateinit var binding: EventDetailFragmentBinding

    private var eventId: String = ""
    private val eventDetailAdapter = EventDetailAdapter(::onBtnBetClick)
    private lateinit var controller: StateVisibilityController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EventDetailFragmentBinding.inflate(inflater, container, false)
        controller = StateVisibilityController(
            progressBar = binding.progressCircular,
            errorView = binding.tvError
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            eventId = EventDetailFragmentArgs.fromBundle(it).eventId
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
        viewModel.eventDetailLiveData.observe(viewLifecycleOwner) {
            updateScreenInfo(it)
        }
    }

    private fun updateScreenInfo(state: eventDetailState) {
        when (state) {
            is State.Content -> showContent(state)
            is State.Error -> controller.showError()
            is State.Loading -> controller.showLoading()
        }
    }

    private fun showContent(content: State.Content<List<EventDetail>>) {
        controller.hideAll()
        eventDetailAdapter.submitList(content.data)
    }

    private fun onBtnBetClick(bet: Bet) {
        val fakeModelFromEvent = ChoiceModel(
            eventId, bet, "First player", "Second player"
        )

        childFragmentManager.beginTransaction()
            .add(BetDialogFragment.newInstance(fakeModelFromEvent), "TAG")
            .commitAllowingStateLoss()
    }
}
