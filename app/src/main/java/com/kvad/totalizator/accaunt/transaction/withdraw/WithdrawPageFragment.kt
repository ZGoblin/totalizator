package com.kvad.totalizator.accaunt.transaction.withdraw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.databinding.WithdrawPageBinding
import com.kvad.totalizator.di.ViewModelFactory
import com.kvad.totalizator.di.injectViewModel
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.StateVisibilityController
import com.kvad.totalizator.tools.hideKeyboard
import com.kvad.totalizator.accaunt.transaction.TransactionErrorState
import com.kvad.totalizator.accaunt.transaction.domain.TransactionType
import com.kvad.totalizator.accaunt.transaction.model.TransactionModel
import javax.inject.Inject

class WithdrawPageFragment : Fragment() {

    private var _binding: WithdrawPageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: WithdrawViewModel
    private lateinit var stateVisibilityController: StateVisibilityController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WithdrawPageBinding.inflate(inflater, container, false)
        setupDi()
        stateVisibilityController = StateVisibilityController(binding.progressWithdraw, null)
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
        stateVisibilityController.hideAll()
        viewModel.withdrawLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> stateVisibilityController.showLoading()
                is State.Error -> setupErrors(it.error)
                is State.Content -> stateVisibilityController.hideLoading()
            }
        }
    }

    private fun setupErrors(error: TransactionErrorState) {
        when (error) {
            TransactionErrorState.LOADING_ERROR -> {
            }
            TransactionErrorState.NO_MONEY -> setupNoMoneyError()
        }
    }

    private fun setupNoMoneyError() {
        MaterialDialog(requireContext()).customView(R.layout.withdraw_error_layout)
            .negativeButton(R.string.close).show()
        stateVisibilityController.hideLoading()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
        viewModel = injectViewModel(viewModelFactory)
    }

    private fun setupTextWatcher() {
        binding.etWithdraw.doOnTextChanged { text, _, _, _ ->
            when {
                text?.isEmpty() == true -> binding.btnWithdraw.isEnabled = false
                else -> binding.btnWithdraw.isEnabled = true
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        stateVisibilityController.destroy()
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = WithdrawPageFragment()
    }
}
