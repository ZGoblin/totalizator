package com.kvad.totalizator.betfeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kvad.totalizator.databinding.BetFragmentBinding

class BetFragment : Fragment() {

    private lateinit var  binding : BetFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BetFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners(){
        binding.btnBet.setOnClickListener {
            Toast.makeText(this.requireContext(), "BET", Toast.LENGTH_SHORT).show()
            childFragmentManager.beginTransaction()
                .add(BetDialogFragment(),"TAG")
                .commitAllowingStateLoss()
        }
    }

}