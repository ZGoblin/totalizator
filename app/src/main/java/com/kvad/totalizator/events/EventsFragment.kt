package com.kvad.totalizator.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import com.kvad.totalizator.App
import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.databinding.EventsFragmentBinding
import com.kvad.totalizator.events.adapter.EventAdapter
import javax.inject.Inject

class EventsFragment : Fragment() {

    @Inject
    lateinit var viewModel: EventsViewModel
    private lateinit var binding: EventsFragmentBinding
    private val eventAdapter = EventAdapter(::onEventClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EventsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDi()
        setupRecycler()
        setupLiveDataObserver()

        viewModel.getEvents()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupLiveDataObserver() {
        viewModel.eventsLiveData.observe(viewLifecycleOwner) {
            updateEvents(it)
        }
    }

    private fun updateEvents(events: List<Event>) {
        eventAdapter.submitList(events)
    }

    private fun setupRecycler() {
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvEvents)
        binding.rvEvents.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun onEventClick(event: Event) {
        val action = EventsFragmentDirections.actionDetailFragment(event.id)
        findNavController().navigate(action)
    }
}
