package com.kvad.totalizator.betfeature

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BetDialogFragmentBinding.inflate(inflater, container, false)
        binding.amountLayout.error = getString(R.string.min_bet)
        binding.etBet.requestFocus()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextWatcher()
        setupListeners()
        setupInputManager()
    }

    private fun setupListeners() {
        binding.tvCancel.setOnClickListener {
            hideKeyBoard()
            dialog?.cancel()
            binding.etBet.clearFocus()
        }
        binding.tvBet.setOnClickListener {
            hideKeyBoard()
            dialog?.cancel()
            binding.etBet.clearFocus()
        }
    }

    //todo
    private fun setupInputManager() {
        val inputMethodManager =
            this.context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.RESULT_SHOWN,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
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
                    if (it.length < 2) {
                        binding.amountLayout.error = getString(R.string.min_bet)
                    } else {
                        binding.amountLayout.error = null
                    }
                }
            }
        }
        binding.etBet.addTextChangedListener(textWatcher)
    }


}

