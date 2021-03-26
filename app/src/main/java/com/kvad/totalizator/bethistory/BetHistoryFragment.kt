package com.kvad.totalizator.bethistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kvad.totalizator.App
import com.kvad.totalizator.databinding.BetDialogFragmentBinding

class BetHistoryFragment : Fragment() {

    private var _binding : BetDialogFragmentBinding? = null
    private val binding get() = _binding!!
    private val betHistoryAdapter = BetHistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BetDialogFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDi()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }



}