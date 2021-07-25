package com.kvad.totalizator.event.feed

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
import com.kvad.totalizator.R
import com.kvad.totalizator.chat.adapter.ChatRecyclerViewAdapter
import com.kvad.totalizator.chat.ui.ChatViewModel
import com.kvad.totalizator.chat.ui.UserMessageUi
import com.kvad.totalizator.event.data.model.Event
import com.kvad.totalizator.databinding.EventsFragmentBinding
import com.kvad.totalizator.di.ViewModelFactory
import com.kvad.totalizator.di.injectViewModel
import com.kvad.totalizator.event.feed.adapter.EventAdapter
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.StateVisibilityController
import com.kvad.totalizator.tools.hideKeyboard
import javax.inject.Inject

@Suppress("TooManyFunctions")
class EventsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var eventsViewModel: EventsViewModel

    private var _binding: EventsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var chatLayoutManager: LinearLayoutManager
    private val chatAdapter = ChatRecyclerViewAdapter()
    private lateinit var stateVisibilityController: StateVisibilityController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EventsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDi()
        setupRecycler()
        setupListener()
        setupLiveDataObserver()
        hideKeyboard()

        binding.apply {
            stateVisibilityController = StateVisibilityController(pbProgress, tvError)
            eventFeedView.setOnEventClick(::onEventClick)
        }
    }

    private fun setupListener() {
        binding.tvSendMessage.setOnClickListener {
            onSendClick()
        }
    }

    private fun onSendClick() {
        binding.apply {
            chatViewModel.sendMessage(etMessage.text.toString())
            etMessage.setText("")
        }
        hideKeyboard()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
        chatViewModel = injectViewModel(viewModelFactory)
        eventsViewModel = injectViewModel(viewModelFactory)
    }

    private fun setupLiveDataObserver() {
        chatViewModel.chatLiveData.observe(viewLifecycleOwner) {
            updateChatState(it)
        }
    }

    private fun updateChatState(state: State<List<UserMessageUi>, ErrorState>) {
        when (state) {
            is State.Content -> {
                updateChatContent(state.data)
                stateVisibilityController.hideAll()
            }
            is State.Error -> updateChatErrorState(state)
            is State.Loading -> stateVisibilityController.showLoading()
        }
    }

    private fun updateChatErrorState(state: State.Error<ErrorState>) {
        when (state.error) {
            ErrorState.LOGIN_ERROR -> findNavController().navigate(R.id.action_to_login)
            ErrorState.LOADING_ERROR -> stateVisibilityController.showError()
        }
    }

    private fun updateChatContent(messages: List<UserMessageUi>) {
        chatAdapter.submitList(messages)
        if (chatLayoutManager.findFirstVisibleItemPosition() < 2) {
            binding.rvChat.layoutManager?.scrollToPosition(0)
        }
    }

    private fun setupRecycler() {
        binding.rvChat.apply {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            adapter = chatAdapter
            chatLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = chatLayoutManager
        }    }

    private fun onEventClick(event: Event) {
        val action = EventsFragmentDirections.actionDetailFragment(event.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        binding.rvChat.adapter = null
        binding.rvChat.layoutManager = null
        _binding = null
        stateVisibilityController.destroy()
        super.onDestroyView()
    }
}
