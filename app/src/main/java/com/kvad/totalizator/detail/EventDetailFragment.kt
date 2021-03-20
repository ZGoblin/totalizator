package com.kvad.totalizator.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kvad.totalizator.App
import com.kvad.totalizator.databinding.EventDetailFragmentBinding

class EventDetailFragment: Fragment() {

    private lateinit var binding: EventDetailFragmentBinding

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
            val eventId = EventDetailFragmentArgs.fromBundle(it).eventId
            Toast.makeText(context, eventId.toString(), Toast.LENGTH_SHORT).show()
        }

        setupDi()
        setupRecyclerView()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupRecyclerView() {

    }
}
