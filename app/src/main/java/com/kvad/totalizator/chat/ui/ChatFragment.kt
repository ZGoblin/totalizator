package com.kvad.totalizator.chat.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SimpleItemAnimator
import com.kvad.totalizator.App
import com.kvad.totalizator.chat.adapter.ChatRecyclerViewAdapter
import com.kvad.totalizator.databinding.ChatFragmentBinding
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.StateVisibilityController
import javax.inject.Inject

class ChatFragment : Fragment() {

    @Inject
    lateinit var viewModel: ChatViewModel

    private var _binding: ChatFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var stateVisibilityController: StateVisibilityController

    private val chatAdapter = ChatRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChatFragmentBinding.inflate(inflater, container, false)
        stateVisibilityController = StateVisibilityController(binding.progressBar, binding.tvError)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDi()
        setupRecycler()
        observeChatLiveData()
    }

    private fun observeChatLiveData() {
        viewModel.chatLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> { }
                is State.Content -> { it.data }
                is State.Error -> {}
            }
        }
    }

    private fun setupRecycler(){
        binding.rvChat.apply {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    override fun onDestroyView() {
        binding.rvChat.adapter = null
        _binding = null
        stateVisibilityController.destroy()
        super.onDestroyView()
    }

}
