package com.kvad.totalizator.chat.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kvad.totalizator.R
import com.kvad.totalizator.databinding.ChatFragmentBinding
import com.kvad.totalizator.databinding.EventsFragmentBinding
import com.kvad.totalizator.tools.StateVisibilityController
import javax.inject.Inject

class ChatFragment : Fragment() {

    //@Inject
    //private lateinit var viewModel: ChatViewModel

    private var _binding: ChatFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var stateVisibilityController: StateVisibilityController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChatFragmentBinding.inflate(inflater, container, false)
        //stateVisibilityController = StateVisibilityController(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        binding.rvChat.adapter = null
        _binding = null
        stateVisibilityController.destroy()
        super.onDestroyView()
    }

}
