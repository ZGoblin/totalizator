package com.kvad.totalizator.transactionfeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.databinding.TransactionViewPagerFragmentBinding
import com.kvad.totalizator.transactionfeature.adapter.TransactionPagerAdapter

class TransactionPagerFragment : Fragment(R.layout.transaction_fragment) {
    private var _binding: TransactionViewPagerFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPagerAdapter: TransactionPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TransactionViewPagerFragmentBinding.inflate(inflater, container, false)
        setupDi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTableLayout()
    }

    private fun setupTableLayout() {
        viewPagerAdapter = TransactionPagerAdapter(this)
        binding.vpTransaction.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tlTransaction, binding.vpTransaction) { tab, position ->
            tab.setText(viewPagerAdapter.getTabTitle(position))
        }.attach()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }


}
