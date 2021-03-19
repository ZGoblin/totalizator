package com.kvad.totalizator.betfeature

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kvad.totalizator.databinding.BetDialogFragmentBinding


class BetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BetDialogFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BetDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isFocusableInTouchMode = true
        binding.etBet.requestFocus()
        val imm = this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        setupListeners()
    }

    private fun setupListeners(){
        binding.tvCancel.setOnClickListener {
            Log.d("Tag", "Cancel")
        }
    }


}
