package com.kvad.totalizator.transactionfeature.withdraw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.kvad.totalizator.App
import com.kvad.totalizator.databinding.WithdrawPageBinding
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.hideKeyboard
import com.kvad.totalizator.transactionfeature.domain.TransactionType
import com.kvad.totalizator.transactionfeature.model.TransactionModel
import javax.inject.Inject

class WithdrawPageFragment : Fragment() {

    private var _binding: WithdrawPageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: WithdrawViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WithdrawPageBinding.inflate(inflater, container, false)
        setupDi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeWithdrawLiveData()
        setupTextWatcher()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnWithdraw.setOnClickListener {
            doWithdraw()
        }
    }

    private fun doWithdraw() {
        val withdraw = TransactionModel(
            amount = binding.etWithdraw.text.toString().toDouble(),
            type = TransactionType.WITHDRAW
        )
        viewModel.doWithdraw(withdraw)
        binding.etWithdraw.text = null
        hideKeyboard()
    }

    private fun observeWithdrawLiveData() {
        viewModel.withdrawLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                }
                is State.Error -> {
                }
                is State.Content -> {
                }
            }
        }
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupTextWatcher() {
        binding.etWithdraw.doOnTextChanged { text, _, _, _ ->
            when {
                text?.isEmpty() == true -> binding.btnWithdraw.isEnabled = false
                else -> binding.btnWithdraw.isEnabled = true
            }
        }
    }

    companion object {
        fun newInstance() = WithdrawPageFragment()
    }
}
