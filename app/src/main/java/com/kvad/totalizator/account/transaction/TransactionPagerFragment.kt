package com.kvad.totalizator.account.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.kvad.totalizator.databinding.TransactionViewPagerFragmentBinding
import com.kvad.totalizator.account.transaction.adapter.TransactionPagerAdapter

class TransactionPagerFragment : Fragment() {
    private var _binding: TransactionViewPagerFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPagerAdapter: TransactionPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TransactionViewPagerFragmentBinding.inflate(inflater, container, false)
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

    override fun onDestroyView() {
        binding.root.layoutAnimation = null
        _binding = null
        super.onDestroyView()
    }


}
