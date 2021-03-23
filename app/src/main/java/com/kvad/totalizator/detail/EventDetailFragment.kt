package com.kvad.totalizator.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.kvad.totalizator.App
import com.kvad.totalizator.R
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
    private var _binding: EventDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val eventDetailAdapter = EventDetailAdapter(::onBtnBetClick)
    private lateinit var controller: StateVisibilityController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EventDetailFragmentBinding.inflate(inflater, container, false)
        controller = StateVisibilityController(
            progressBar = binding.progressCircular,
            errorView = binding.tvError
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDi()
        setupRecyclerView()
        setupListener()
        setupViewModelObserver()

        arguments?.let {
            viewModel.uploadData(EventDetailFragmentArgs.fromBundle(it).eventId)
        }
    }

    private fun setupListener() {
        binding.tvBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupRecyclerView() {
        binding.rvEventDetailInfo.apply {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
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
        controller.hideAll()
        when (state) {
            is State.Content -> showContent(state)
            is State.Error -> controller.showError()
            is State.Loading -> controller.showLoading()
        }
    }

    private fun showContent(content: State.Content<List<EventDetail>>) {
        eventDetailAdapter.submitList(content.data)
    }

    override fun onDestroyView() {
        controller.destroy()
        binding.rvEventDetailInfo.adapter = null
        _binding = null
        super.onDestroyView()
    }

    private fun onBtnBetClick(bet: Bet) {
        val action = EventDetailFragmentDirections.actionBetDialogFragment(bet)
        findNavController().navigate(action)
    }
}
