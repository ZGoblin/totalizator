package com.kvad.totalizator.transactionfeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.databinding.OnBoardBinding
import com.kvad.totalizator.databinding.TransactionViewPagerFragmentBinding
import javax.inject.Inject

class TransactionPagerFragment : Fragment(R.layout.transaction_fragment) {
    private var _binding: TransactionViewPagerFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TransactionViewPagerFragmentBinding.inflate(inflater,container,false)
        setupDi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTableLayout()
    }

    private fun setupTableLayout() {
        binding.vpTransaction.adapter = TransactionPagerAdapter(this)
        TabLayoutMediator(binding.tlTransaction, binding.vpTransaction) { tab, position ->
            tab.text = if (position == 0) "Withdraw" else "Deposit"
        }.attach()
    }

    private fun setupDi(){
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }



}