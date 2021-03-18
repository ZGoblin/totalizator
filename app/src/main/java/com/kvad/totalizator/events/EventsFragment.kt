package com.kvad.totalizator.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kvad.totalizator.databinding.EventsFragmentBinding
import com.kvad.totalizator.events.adapter.EventsAdapter

class EventsFragment : Fragment() {

    private lateinit var binding: EventsFragmentBinding
    private val eventAdapter = EventsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EventsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
    }

    private fun setupRecycler() {
        binding.rvEvents.adapter = eventAdapter
        binding.rvEvents.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val list = mutableListOf<EventResponse>()

        for(i in 1..10) {
            list.add(EventResponse(i))
        }

        eventAdapter.submitList(list)
    }
}
