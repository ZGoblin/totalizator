package com.kvad.totalizator.transactionfeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.databinding.TransactionFragmentBinding
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.StateVisibilityController
import javax.inject.Inject

class TransactionFragment : Fragment() {

    private lateinit var binding: TransactionFragmentBinding
    private lateinit var transactionState: TransactionState
    private lateinit var progress: StateVisibilityController

    @Inject
    lateinit var viewModel: TransactionViewModel

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
        progress = StateVisibilityController(binding.progressBarr, null)
        setupDi()
        setupListeners()
        setupTextWatcher()
        setupState()
        binding.etTransaction.requestFocus()
        observeLiveData()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupState() {
        transactionState = TransactionState.DEPOSIT
    }

    private fun doTransaction() {
        val deposit = TransactionModel(
            amount = binding.etTransaction.text.toString().toDouble(),
            type = transactionState
        )
        viewModel.doDeposit(deposit)
        binding.etTransaction.text = null
    }
	
    private fun observeLiveData() {
        progress.hideAll()
        viewModel.transactionLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> progress.showLoading()
                is State.Error -> {
                }
                is State.Content -> progress.hideAll()
            }
        }
    }

    private fun setupListeners() {
        binding.tbChoiceTransaction.setOnCheckedChangeListener { _, isChecked ->
            setupCheck(isChecked)
        }

        binding.btnTransaction.setOnClickListener {
            doTransaction()
        }
    }

    private fun setupCheck(check: Boolean) {
        when (check) {
            true -> {
                binding.btnTransaction.text = getString(R.string.deposit)
                transactionState = TransactionState.DEPOSIT
            }
            false -> {
                binding.btnTransaction.text = getString(R.string.withdraw)
                transactionState = TransactionState.WITHDRAW
            }
        }
    }

    private fun setupTextWatcher() {
        binding.etTransaction.doOnTextChanged { text, _, _, _ ->
            when {
                text?.isEmpty() == true -> {
                    binding.btnTransaction.apply {
                        isEnabled = false
                        val colorBackGround =
                            ContextCompat.getColor(requireContext(), R.color.light_grey)
                        binding.btnTransaction.setBackgroundColor(colorBackGround)
                        val colorForeGround =
                            ContextCompat.getColor(requireContext(), R.color.black)
                        binding.btnTransaction.apply {
                            setBackgroundColor(colorBackGround)
                            setTextColor(colorForeGround)
                        }
                    }
                }
                else -> {
                    binding.btnTransaction.apply {
                        isEnabled = true
                        val colorBackGround =
                            ContextCompat.getColor(requireContext(), R.color.lite_green)
                        val colorForeGround =
                            ContextCompat.getColor(requireContext(), R.color.white)
                        binding.btnTransaction.apply {
                            setBackgroundColor(colorBackGround)
                            setTextColor(colorForeGround)
                        }
                    }
                }
            }
        }

    }
}


