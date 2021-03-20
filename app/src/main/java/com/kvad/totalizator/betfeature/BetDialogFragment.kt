package com.kvad.totalizator.betfeature

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kvad.totalizator.R
import com.kvad.totalizator.betfeature.model.ChoiceModel
import com.kvad.totalizator.databinding.BetDialogFragmentBinding
import com.kvad.totalizator.tools.BET_DETAIL_KEY
import javax.inject.Inject

class BetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BetDialogFragmentBinding
    private var coefficient: Int = 1
    private lateinit var detailBet: ChoiceModel

    @Inject
    lateinit var viewModel: BetViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BetDialogFragmentBinding.inflate(inflater, container, false)
        binding.amountLayout.error = getString(R.string.min_bet)
        binding.etBet.requestFocus()
        coefficient = 2
        arguments?.let {
            detailBet = it.getParcelable(BET_DETAIL_KEY) ?: ChoiceModel(
                ChoiceState.DRAW,
                CommandInfoSum("Error", 1), CommandInfoSum("Error", 1)
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        setupTextWatcher()
        setupListeners()
    }

    private fun setupData() {
        binding.tvGameDetails.text =
            getString(R.string.event_vs, detailBet.commandFirst.name, detailBet.commandSecond.name)
        when (detailBet.choiceState) {
            ChoiceState.FIRST_PLAYER_WIN -> {
                binding.tvWinnerName.text = detailBet.commandFirst.name
            }
            ChoiceState.SECOND_PLAYER_WIN -> {
                binding.tvWinnerName.text = detailBet.commandSecond.name
            }
            ChoiceState.DRAW -> {
                binding.tvWinnerName.text = getString(R.string.draw)
            }
        }
    }

    private fun setupListeners() {
        binding.tvCancel.setOnClickListener {
            cancelBetDialog()
        }
        binding.btnBet.setOnClickListener {
            //todo sendBet
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

    private fun setupTextWatcher() {
        binding.etBet.doOnTextChanged { text, _, _, _ ->
            when {
                text?.isEmpty() == true -> {
                    binding.apply {
                        amountLayout.error = getString(R.string.min_bet)
                        btnBet.isEnabled = false
                        val color = resources.getColor(R.color.light_grey)
                        btnBet.setBackgroundColor(color)
                    }
                }
                text?.length == 1 -> {
                    binding.apply {
                        amountLayout.error = getString(R.string.min_bet)
                        btnBet.isEnabled = false
                        val color = resources.getColor(R.color.light_grey)
                        btnBet.setBackgroundColor(color)
                    }
                }
                else -> {
                    binding.apply {
                        amountLayout.error = null
                        btnBet.isEnabled = true
                        val color = resources.getColor(R.color.yellow)
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


