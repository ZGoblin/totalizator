package com.kvad.totalizator.transactionfeature.deposit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.kvad.totalizator.App
import com.kvad.totalizator.databinding.DepositPageBinding
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.StateVisibilityController
import com.kvad.totalizator.tools.hideKeyboard
import com.kvad.totalizator.transactionfeature.domain.TransactionType
import com.kvad.totalizator.transactionfeature.model.TransactionModel
import javax.inject.Inject

class DepositPageFragment : Fragment() {

    private var _binding: DepositPageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: DepositViewModel
    lateinit var stateVisibilityController: StateVisibilityController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DepositPageBinding.inflate(inflater, container, false)
        setupDi()
        stateVisibilityController = StateVisibilityController(binding.progressDeposit,binding.tvErrorDeposit)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeDepositLiveData()
        setupTextWatcher()
        setupListeners()
    }

    private fun setupListeners(){
        binding.btnDeposit.setOnClickListener {
            doDeposit()
        }
    }

    private fun observeDepositLiveData(){
        stateVisibilityController.hideAll()
        viewModel.depositLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> stateVisibilityController.showLoading()
                is State.Error -> stateVisibilityController.showError()
                is State.Content -> stateVisibilityController.hideAll()
            }
        }
    }

    private fun setupError(){

    }

    private fun doDeposit(){
        val deposit = TransactionModel(
            amount = binding.etDeposit.text.toString().toDouble(),
            type = TransactionType.DEPOSIT
        )
        viewModel.deDeposit(deposit)
        binding.etDeposit.text = null
        hideKeyboard()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun setupTextWatcher(){
        binding.etDeposit.doOnTextChanged { text, _, _, _ ->
            when{
                text?.isEmpty() == true -> binding.btnDeposit.isEnabled = false
                else -> binding.btnDeposit.isEnabled = true
            }
        }
    }
    override fun onDestroyView() {
        _binding = null
        stateVisibilityController.destroy()
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = DepositPageFragment()
    }

}
