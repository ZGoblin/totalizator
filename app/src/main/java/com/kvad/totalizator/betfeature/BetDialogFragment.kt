package com.kvad.totalizator.betfeature

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.data.model.Event
import com.kvad.totalizator.databinding.BetDialogFragmentBinding
import com.kvad.totalizator.detail.EventDetailFragment
import com.kvad.totalizator.detail.EventDetailFragmentArgs
import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.tools.BET_DETAIL_KEY
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.StateVisibilityController
import javax.inject.Inject

@Suppress("TooManyFunctions")
class BetDialogFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModel: BetViewModel

    private lateinit var binding: BetDialogFragmentBinding
    private lateinit var detailBet: Bet
    private var eventId: String = ""
    private var coefficient: Float = 0f
    private lateinit var stateVisibilityController: StateVisibilityController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BetDialogFragmentBinding.inflate(inflater, container, false)
        arguments?.let {
            val safeArgs = BetDialogFragmentArgs.fromBundle(it).bet
            detailBet = safeArgs
        }
        stateVisibilityController = StateVisibilityController(binding.progressBarCircular, null)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDi()
        setupListeners()
        binding.amountLayout.error = getString(R.string.min_bet)
        binding.etBet.requestFocus()
        viewModel.uploadData()
        setupTextWatcher()
        observeEventInfoLiveData()
        observeBetLiveData()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupListeners() {
        binding.tvCancel.setOnClickListener {
            cancelBetDialog()
        }
        binding.btnBet.setOnClickListener {
            val amount = binding.etBet.text.toString().toDouble()
            val betToServerModel = BetToServerModel(
                eventId, amount, detailBet
            )
            viewModel.createBet(betToServerModel)
        }
        binding.vClose.setOnClickListener {
            cancelBetDialog()
        }
    }


    private fun cancelBetDialog() {
        hideKeyBoard()
        dialog?.cancel()
        binding.etBet.clearFocus()
    }

    private fun hideKeyBoard() {
        val inputMethodManager =
            this.context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.applicationWindowToken, 0)
    }

    private fun observeBetLiveData() {
        stateVisibilityController.hideAll()
        viewModel.betDetailLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> stateVisibilityController.showLoading()
                is State.Content -> setupBetUiResult()
                is State.Error -> findNavController().navigate(R.id.login_fragment)
            }
        }
    }

    private fun setupBetUiResult() {
        binding.apply {
            etBet.visibility = View.GONE
            tvCancel.visibility = View.GONE
            tvBetGood.visibility = View.VISIBLE
            vClose.visibility = View.VISIBLE
            btnBet.isEnabled = false
            val color = ContextCompat.getColor(requireContext(), R.color.light_grey)
            btnBet.setBackgroundColor(color)
            hideKeyBoard()
        }
    }

    private fun observeEventInfoLiveData() {
        stateVisibilityController.hideAll()
        viewModel.eventInfoLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> stateVisibilityController.showLoading()
                is State.Content -> setupInfoForBet(it)
                is State.Error -> cancelBetDialog()
            }
        }
    }

    private fun setupInfoForBet(state: State.Content<BetDetail>) {
        binding.apply {
            tvGameDetails.text = getString(
                R.string.event_vs,
                state.data.firstPlayerName,
                state.data.secondPlayerName
            )
            tvWinnerName.text = when (detailBet) {
                Bet.DRAW -> getString(R.string.draw)
                Bet.SECOND_PLAYER_WIN -> state.data.firstPlayerName
                Bet.FIRST_PLAYER_WIN -> state.data.secondPlayerName
            }
        }
        eventId = state.data.eventId
        //Toast.makeText(requireContext(),state.data.margin.toString(),Toast.LENGTH_SHORT).show()
        coefficient = viewModel.calculate(detailBet)
    }

    private fun setupTextWatcher() {
        binding.etBet.doOnTextChanged { text, _, _, _ ->
            when {
                text?.isEmpty() == true -> {
                    binding.apply {
                        amountLayout.error = getString(R.string.min_bet)
                        btnBet.isEnabled = false
                        val color = ContextCompat.getColor(requireContext(), R.color.light_grey)
                        btnBet.setBackgroundColor(color)
                        btnBet.text = getString(R.string.do_bet_simple)
                        tvPraise.visibility = View.INVISIBLE
                    }
                }
                text?.length == 1 -> {
                    val possibleGain = (coefficient * binding.etBet.text.toString().toFloat())
                    binding.apply {
                        amountLayout.error = getString(R.string.min_bet)
                        btnBet.isEnabled = false
                        val color = ContextCompat.getColor(requireContext(), R.color.light_grey)
                        btnBet.setBackgroundColor(color)
                        btnBet.text = getString(R.string.do_bet, binding.etBet.text.toString().toFloat())
                        tvPraise.visibility = View.VISIBLE
                        tvPraise.text = getString(R.string.possible_gain, possibleGain)
                    }
                }
                else -> {
                    val possibleGain = (coefficient * binding.etBet.text.toString().toFloat())
                    binding.apply {
                        amountLayout.error = null
                        btnBet.isEnabled = true
                        val color = ContextCompat.getColor(requireContext(), R.color.yellow)
                        btnBet.setBackgroundColor(color)
                        btnBet.text = getString(R.string.do_bet, binding.etBet.text.toString().toFloat())
                        tvPraise.visibility = View.VISIBLE
                        tvPraise.text = getString(R.string.possible_gain, possibleGain)
                    }
                }
            }
        }
    }

}



