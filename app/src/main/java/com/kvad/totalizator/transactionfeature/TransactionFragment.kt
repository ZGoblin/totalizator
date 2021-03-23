package com.kvad.totalizator.transactionfeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.databinding.TransactionFragmentBinding

class TransactionFragment : Fragment() {

    private lateinit var binding: TransactionFragmentBinding
    private lateinit var transactionState: TransactionState
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = TransactionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDi()
        setupListeners()
        setupTextWatcher()
        setupState()
        binding.etTransaction.requestFocus()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupState(){
        transactionState = TransactionState.DEPOSIT
    }


    private fun setupListeners() {
        binding.tbChoiceTransaction.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(
                    requireContext(),
                    binding.tbChoiceTransaction.textOff.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                binding.btnTransaction.text = "Поповнити рахунок"
                transactionState = TransactionState.DEPOSIT
            } else {
                Toast.makeText(
                    requireContext(),
                    binding.tbChoiceTransaction.textOn.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                binding.btnTransaction.text = "Снять деньги"
                transactionState = TransactionState.WITHDRAW
            }
        }
    }

    private fun setupTextWatcher() {
        binding.etTransaction.doOnTextChanged { text, _, _, _ ->
            when {
                text?.isEmpty() == true -> {
                    binding.btnTransaction.apply{
                        isEnabled = false
                        val colorBackGround = ContextCompat.getColor(requireContext(), R.color.light_grey)
                        binding.btnTransaction.setBackgroundColor(colorBackGround)
                        val colorForeGround = ContextCompat.getColor(requireContext(), R.color.black)
                        binding.btnTransaction.setTextColor(colorForeGround)
                    }
                }
                else -> {
                    binding.btnTransaction.apply{
                        isEnabled = true
                        val color = ContextCompat.getColor(requireContext(), R.color.lite_green)
                        binding.btnTransaction.setBackgroundColor(color)
                        val colorForeGround = ContextCompat.getColor(requireContext(), R.color.white)
                        binding.btnTransaction.setTextColor(colorForeGround)
                    }
                }
            }
        }

    }
}