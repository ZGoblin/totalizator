package com.kvad.totalizator.transactionfeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.databinding.TransactionFragmentBinding
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.StateVisibilityController
import com.kvad.totalizator.transactionfeature.model.TransactionModel
import javax.inject.Inject

class TransactionFragment : Fragment() {

    private lateinit var binding: TransactionFragmentBinding
    private lateinit var transactionState: TransactionType
    private lateinit var stateVisibilityController: StateVisibilityController

    @Inject
    lateinit var viewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TransactionFragmentBinding.inflate(inflater, container, false)
        stateVisibilityController = StateVisibilityController(binding.progressBarr, binding.tvError)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        transactionState = TransactionType.WITHDRAW
    }

    private fun doTransaction() {
        val deposit = TransactionModel(
            amount = binding.etTransaction.text.toString().toDouble(),
            type = transactionState
        )
        viewModel.doTransaction(deposit)
        binding.etTransaction.text = null
    }

    private fun observeLiveData() {
        stateVisibilityController.hideAll()
        viewModel.transactionLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> stateVisibilityController.showLoading()
                is State.Error -> setupError(it.error)
                is State.Content -> stateVisibilityController.hideAll()
            }
        }
    }

    private fun setupError(error: TransactionState) {
        when (error) {
            TransactionState.NO_MONEY -> {
                stateVisibilityController.showError()
                binding.etTransaction.text = null
                binding.btnTransaction.isEnabled = false
            }
            TransactionState.LOADING -> { }
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
                transactionState = TransactionType.DEPOSIT
            }
            false -> {
                binding.btnTransaction.text = getString(R.string.withdraw)
                transactionState = TransactionType.WITHDRAW
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


