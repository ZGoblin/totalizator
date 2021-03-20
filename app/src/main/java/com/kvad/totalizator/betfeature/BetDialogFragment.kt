package com.kvad.totalizator.betfeature

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kvad.totalizator.R
import com.kvad.totalizator.databinding.BetDialogFragmentBinding

class BetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BetDialogFragmentBinding
    private var coefficient: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BetDialogFragmentBinding.inflate(inflater, container, false)
        binding.amountLayout.error = getString(R.string.min_bet)
        binding.etBet.requestFocus()
        coefficient = 2
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextWatcher()
        setupListeners()
    }

    private fun setupListeners() {
        binding.tvCancel.setOnClickListener {
            cancelBetDialog()
        }
        binding.btnBet.setOnClickListener {
            cancelBetDialog()
        }
    }

    private fun cancelBetDialog(){
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
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.also {
                    when {
                        it.isEmpty() -> {
                            binding.apply {
                                amountLayout.error = getString(R.string.min_bet)
                                btnBet.isEnabled = false
                                btnBet.text = getString(R.string.bet)
                                val color = resources.getColor(R.color.light_grey)
                                btnBet.setBackgroundColor(color)
                            }
                        }
                        it.length == 1 -> {
                            val possibleGain = (coefficient * binding.etBet.text.toString().toInt())
                            binding.apply {
                                amountLayout.error = getString(R.string.min_bet)
                                btnBet.isEnabled = false
                                val color = resources.getColor(R.color.light_grey)
                                btnBet.setBackgroundColor(color)
                                btnBet.text = getString(R.string.possible_gain, possibleGain)
                            }
                        }
                        else -> {
                            val possibleGain = (coefficient * binding.etBet.text.toString().toInt())
                            binding.apply {
                                amountLayout.error = null
                                btnBet.isEnabled = true
                                val color = resources.getColor(R.color.light_grey)
                                btnBet.setBackgroundColor(color)
                                btnBet.text = getString(R.string.possible_gain, possibleGain)
                            }
                        }
                    }
                }
            }
        }
        binding.etBet.addTextChangedListener(textWatcher)
    }
}

