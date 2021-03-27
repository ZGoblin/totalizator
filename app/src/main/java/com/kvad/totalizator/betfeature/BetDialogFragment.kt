package com.kvad.totalizator.betfeature

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.betfeature.domain.BetState
import com.kvad.totalizator.betfeature.model.BetDetail
import com.kvad.totalizator.betfeature.model.BetToServerModel
import com.kvad.totalizator.databinding.BetDialogFragmentBinding
import com.kvad.totalizator.di.ViewModelFactory
import com.kvad.totalizator.di.injectViewModel
import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.hideKeyboard
import com.kvad.totalizator.tools.disableDragging
import com.kvad.totalizator.tools.StateVisibilityController
import javax.inject.Inject

@Suppress("TooManyFunctions")
class BetDialogFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: BetViewModel
    private var _binding: BetDialogFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailBet: Bet
    private lateinit var detailId: String
    private var eventId: String = ""
    private lateinit var stateVisibilityController: StateVisibilityController

    private fun Dialog.initDialogShowListener() {
        setOnShowListener { dialog ->
            (dialog as BottomSheetDialog).findViewById<View>(R.id.design_bottom_sheet)
                ?.let { view ->
                    BottomSheetBehavior.from(view).apply {
                        disableDragging()
                        setState(BottomSheetBehavior.STATE_EXPANDED)
                    }
                }
            setCancelable(true)
            setCanceledOnTouchOutside(false)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.QuickBetDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            initDialogShowListener()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BetDialogFragmentBinding.inflate(inflater, container, false)
        arguments?.let {
            val bet = BetDialogFragmentArgs.fromBundle(it).bet
            val eventId = BetDialogFragmentArgs.fromBundle(it).eventId
            detailBet = bet
            detailId = eventId
        }
        stateVisibilityController =
            StateVisibilityController(binding.progressBarCircular, null)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDi()
        setupListeners()
        setupTextWatcher()
        observeBetInfoLiveData()
        observeDoBetLiveData()
        viewModel.uploadData(detailId)
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
        viewModel = injectViewModel(viewModelFactory)
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
            binding.btnBet.isEnabled = false
            binding.tvCancel.isEnabled = false

        }
        binding.vClose.setOnClickListener {
            cancelBetDialog()
        }
    }

    private fun observeDoBetLiveData() {
        stateVisibilityController.hideAll()
        viewModel.betLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> stateVisibilityController.showLoading()
                is State.Content -> setupDoBetSuccess()
                is State.Error -> {
                    setupDoBetError(it.error)
                    stateVisibilityController.showError()
                }
            }
        }
    }

    private fun setupDoBetError(error: BetState) {
        stateVisibilityController.hideAll()
        when (error) {
            BetState.LOGIN_ERROR -> findNavController().navigate(R.id.login_fragment)
            BetState.LOADING_ERROR -> cancelBetDialog()
            BetState.NO_MONEY_LEFT -> setupNoMoneyError()
        }
    }

    private fun setupNoMoneyError() {
        MaterialDialog(requireContext()).customView(R.layout.bet_no_enough_money_error_layout)
            .positiveButton(R.string.deposit, click = {
                findNavController().navigate(R.id.transaction_pager)
            }).negativeButton(R.string.close).show()
    }

    private fun setupDoBetSuccess() {
        stateVisibilityController.hideAll()
        binding.apply {
            etBet.visibility = View.GONE
            tvCancel.visibility = View.GONE
            tvBetGood.visibility = View.VISIBLE
            vClose.visibility = View.VISIBLE
            btnBet.visibility = View.GONE
            tvPraise.visibility = View.GONE
        }
        hideKeyboard()
    }

    private fun observeBetInfoLiveData() {
        stateVisibilityController.hideAll()
        viewModel.betInfoLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    stateVisibilityController.showLoading()
                }
                is State.Content -> {
                    stateVisibilityController.hideAll()
                    setupBetInfo(it)
                    calculateAndSetupUi()
                }
                is State.Error -> {
                    stateVisibilityController.showError()
                }
            }
        }
    }

    private fun setupBetInfo(state: State.Content<BetDetail>) {
        eventId = state.data.eventId
        binding.apply {
            tvGameDetails.text = getString(
                R.string.event_vs,
                state.data.firstPlayerName,
                state.data.secondPlayerName
            )
            tvWinnerName.text = when (detailBet) {
                Bet.DRAW -> getString(R.string.draw)
                Bet.FIRST_PLAYER_WIN -> state.data.firstPlayerName
                Bet.SECOND_PLAYER_WIN -> state.data.secondPlayerName
            }
        }
    }

    private fun setupTextWatcher() {
        binding.amountLayout.error = getString(R.string.min_bet)
        binding.etBet.requestFocus()
        binding.etBet.doOnTextChanged { text, _, _, _ ->
            when {
                text?.isEmpty() == true -> checkTextIsEmpty()
                text?.length == 1 -> checkTextLength(false)
                else -> checkTextLength(true)
            }
        }
    }


    private fun checkTextIsEmpty() {

        binding.amountLayout.error = getString(R.string.min_bet)
        binding.btnBet.isEnabled = false
        binding.btnBet.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.grey
            )
        )
        binding.btnBet.text = getString(R.string.do_bet_simple)
        binding.tvPraise.visibility = View.INVISIBLE
    }

    private fun checkTextLength(btnBetEnable: Boolean) {
        when (btnBetEnable) {
            true -> {
                binding.apply {
                    amountLayout.error = null
                    btnBet.isEnabled = true
                    btnBet.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.yellow
                        )
                    )
                }
            }
            false -> {
                binding.apply {
                    amountLayout.error = getString(R.string.min_bet)
                    btnBet.isEnabled = false
                    btnBet.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.grey
                        )
                    )
                }
            }
        }
        binding.apply {
            btnBet.text = getString(R.string.do_bet, binding.etBet.text.toString().toFloat())
            tvPraise.visibility = View.VISIBLE
        }
        if (!binding.progressBarCircular.isVisible) {
            calculateAndSetupUi()
        }
    }

    private fun calculateAndSetupUi() {
        if (!binding.etBet.text.isNullOrEmpty()) {
            val coefficient =
                viewModel.calculate(detailBet, binding.etBet.text.toString().toFloat())
            val possibleGain = (coefficient * binding.etBet.text.toString().toFloat())
            binding.tvPraise.text = getString(R.string.possible_gain, possibleGain)
        }
    }

    private fun cancelBetDialog() {
        dialog?.cancel()
        binding.etBet.clearFocus()
    }

    override fun onDestroyView() {
        _binding = null
        stateVisibilityController.destroy()
        super.onDestroyView()
    }

}
