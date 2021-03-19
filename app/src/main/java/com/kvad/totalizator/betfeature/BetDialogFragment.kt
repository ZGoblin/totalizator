package com.kvad.totalizator.betfeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

}