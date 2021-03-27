package com.kvad.totalizator.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.kvad.totalizator.App
import com.kvad.totalizator.chat.adapter.ChatRecyclerViewAdapter
import com.kvad.totalizator.chat.ui.ChatViewModel
import com.kvad.totalizator.chat.ui.UserMessageUi
import com.kvad.totalizator.databinding.EventDetailFragmentBinding
import com.kvad.totalizator.detail.adapter.EventDetailAdapter
import com.kvad.totalizator.detail.model.EventDetail
import com.kvad.totalizator.di.ViewModelFactory
import com.kvad.totalizator.di.injectViewModel
import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.StateVisibilityController
import com.kvad.totalizator.tools.hideKeyboard
import javax.inject.Inject

@Suppress("TooManyFunctions")
class EventDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: EventDetailViewModel
    lateinit var chatViewModel: ChatViewModel

    private var _binding: EventDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val chatAdapter = ChatRecyclerViewAdapter()
    private lateinit var chatLayoutManager: LinearLayoutManager
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
        showChat(false)

        arguments?.let {
            viewModel.uploadData(EventDetailFragmentArgs.fromBundle(it).eventId)
        }
    }

    private fun showChat(show: Boolean) {
        binding.apply {
            rvChat.isVisible = show
            etMessage.isVisible = show
            tvSendMessage.isVisible = show
        }
    }

    private fun setupListener() {
        binding.tvBack.setOnClickListener {
            findNavController().popBackStack()
        }
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
        viewModel = injectViewModel(viewModelFactory)
        chatViewModel = injectViewModel(viewModelFactory)
    }

    private fun setupRecyclerView() {
        setupChatRecycler()
        setupEventRecycler()
    }

    private fun setupEventRecycler() {
        binding.rvEventDetailInfo.apply {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            adapter = eventDetailAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupChatRecycler() {
        binding.rvChat.apply {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            adapter = chatAdapter
            chatLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = chatLayoutManager
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
        openLiveChat()
        eventDetailAdapter.submitList(content.data)
    }

    private fun openLiveChat() {
        if (viewModel.isLive) {
            binding.rvEventDetailInfo.layoutParams =
                ConstraintLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            chatViewModel.chatLiveData.observe(viewLifecycleOwner) {
                updateChatState(it)
            }
            showChat(true)
        }
    }

    private fun updateChatState(state: State<List<UserMessageUi>, ErrorState>) {
        when (state) {
            is State.Content -> updateChatContent(state.data)
            else -> showChat(false)
        }
    }

    private fun updateChatContent(messages: List<UserMessageUi>) {
        chatAdapter.submitList(messages)
        if (chatLayoutManager.findFirstVisibleItemPosition() < 2) {
            binding.rvChat.layoutManager?.scrollToPosition(0)
        }
    }

    override fun onDestroyView() {
        controller.destroy()
        binding.rvEventDetailInfo.adapter = null
        _binding = null
        super.onDestroyView()
    }

    private fun onBtnBetClick(bet: Bet, id: String) {
        val action = EventDetailFragmentDirections.actionBetDialogFragment(bet, id)
        findNavController().navigate(action)
    }
}
