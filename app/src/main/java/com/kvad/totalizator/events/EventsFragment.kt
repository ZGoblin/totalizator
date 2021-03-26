package com.kvad.totalizator.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SimpleItemAnimator
import com.kvad.totalizator.App
import com.kvad.totalizator.chat.adapter.ChatRecyclerViewAdapter
import com.kvad.totalizator.chat.ui.ChatViewModel
import com.kvad.totalizator.chat.ui.UserMessageUi
import com.kvad.totalizator.data.model.Event
import com.kvad.totalizator.databinding.EventsFragmentBinding
import com.kvad.totalizator.events.adapter.EventAdapter
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.StateVisibilityController
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.hideKeyboard
import javax.inject.Inject

@Suppress("TooManyFunctions")
class EventsFragment : Fragment() {

    @Inject
    lateinit var viewModel: EventsViewModel

    @Inject
    lateinit var chatViewModel: ChatViewModel
    private var _binding: EventsFragmentBinding? = null
    private val binding get() = _binding!!
    private val chatAdapter = ChatRecyclerViewAdapter()
    private val eventAdapter = EventAdapter(::onEventClick)
    private lateinit var stateVisibilityController: StateVisibilityController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EventsFragmentBinding.inflate(inflater, container, false)
        stateVisibilityController =
            StateVisibilityController(progressBar = binding.pbProgress, errorView = binding.tvError)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDi()
        setupRecycler()
        setupListener()
        setupLiveDataObserver()
        hideKeyboard()
    }

    private fun setupListener() {
        binding.tvSendMessage.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage(){
        chatViewModel.sendMessage(binding.etMessage.text.toString())
        binding.etMessage.text = null
        hideKeyboard()
    }


    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupLiveDataObserver() {
        viewModel.eventsLiveData.observe(viewLifecycleOwner) {
            updateEvents(it)
        }
        chatViewModel.chatLiveData.observe(viewLifecycleOwner) {
            updateChat(it)
        }
    }

    private fun updateChat(state: State<List<UserMessageUi>, ErrorState>) {
        when (state) {
            is State.Loading -> {
            }
            is State.Content -> {
                chatAdapter.submitList(state.data.reversed())
            }
            is State.Error -> {
            }
        }
    }

    private fun updateEvents(state: State<List<Event>, ErrorState>) {
        stateVisibilityController.hideAll()
        when (state) {
            is State.Content -> eventAdapter.submitList(state.data)
            is State.Error -> {
                stateVisibilityController.showError()
            }
            is State.Loading -> {
                if (eventAdapter.itemCount <= 0) {
                    stateVisibilityController.showLoading()
                }
            }
        }
    }

    private fun setupRecycler() {
        setupEventsRecycler()
        setupChatRecycler()
    }

    private fun setupChatRecycler() {
        binding.rvChat.apply {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupEventsRecycler() {
        binding.rvEvents.apply {
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun onEventClick(event: Event) {
        val action = EventsFragmentDirections.actionDetailFragment(event.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        binding.rvEvents.adapter = null
        _binding = null
        stateVisibilityController.destroy()
        super.onDestroyView()
    }
}
