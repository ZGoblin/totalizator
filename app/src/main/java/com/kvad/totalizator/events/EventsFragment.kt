package com.kvad.totalizator.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kvad.totalizator.databinding.EventsFragmentBinding

class EventsFragment: Fragment() {

    private lateinit var binding: EventsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EventsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
