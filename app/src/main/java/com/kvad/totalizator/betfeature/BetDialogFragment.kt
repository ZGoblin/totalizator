package com.kvad.totalizator.betfeature

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.betfeature.model.ChoiceModel
import com.kvad.totalizator.databinding.BetDialogFragmentBinding
import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.tools.BET_DETAIL_KEY
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import javax.inject.Inject

class BetDialogFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModel: BetViewModel

    private lateinit var binding: BetDialogFragmentBinding
    private lateinit var detailBet: ChoiceModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BetDialogFragmentBinding.inflate(inflater, container, false)
        arguments?.let {
            detailBet =
                it.getParcelable(BET_DETAIL_KEY) ?: ChoiceModel("1", Bet.DRAW, "Error", "Error")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDi()
        setupData()
        setupTextWatcher()
        setupListeners()
        observeViewModel()

        binding.amountLayout.error = getString(R.string.min_bet)
        binding.etBet.requestFocus()
        viewModel.setupData(detailBet.eventId, detailBet.betState)

    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupData() {
        binding.tvGameDetails.text =
            getString(R.string.event_vs, detailBet.firstPlayerName, detailBet.secondPlayerName)
        binding.tvWinnerName.text = when (detailBet.betState) {
            Bet.FIRST_PLAYER_WIN -> detailBet.firstPlayerName
            Bet.SECOND_PLAYER_WIN -> detailBet.secondPlayerName
            Bet.DRAW -> getString(R.string.draw)
        }
    }

    private fun setupListeners() {
        binding.tvCancel.setOnClickListener {
            cancelBetDialog()
        }
        binding.btnBet.setOnClickListener {
            val amount = binding.etBet.text.toString().toInt()
            viewModel.createBet(amount)
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

    private fun observeViewModel() {
        viewModel.betDetailLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> MaterialDialog(requireContext()).show { customView(R.layout.progress_dialog) }
                is State.Content -> cancelBetDialog()
                is State.Error -> handleErrors(it.error)
            }
        }
    }

    private fun handleErrors(errorState: ErrorState) {
        when (errorState) {
            ErrorState.LOGIN_ERROR -> findNavController().navigate(R.id.login_fragment)
            ErrorState.LOADING_ERROR -> { }
        }
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
                    }
                }
                text?.length == 1 -> {
                    binding.apply {
                        amountLayout.error = getString(R.string.min_bet)
                        btnBet.isEnabled = false
                        val color = ContextCompat.getColor(requireContext(), R.color.light_grey)
                        btnBet.setBackgroundColor(color)
                    }
                }
                else -> {
                    binding.apply {
                        amountLayout.error = null
                        btnBet.isEnabled = true
                        val color = ContextCompat.getColor(requireContext(), R.color.yellow)
                        btnBet.setBackgroundColor(color)
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(choice: ChoiceModel) =
            BetDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(BET_DETAIL_KEY, choice)
                }
            }
    }
}


