package com.kvad.totalizator.header

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kvad.totalizator.App
import com.kvad.totalizator.data.models.Wallet
import com.kvad.totalizator.databinding.HeaderFragmentBinding
import javax.inject.Inject

class HeaderFragment: Fragment() {

    @Inject
    lateinit var viewModel: HeaderViewModel
    private lateinit var binding: HeaderFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HeaderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDi()
        setupLiveDataObserver()

        viewModel.getWallet()
    }

    private fun setupLiveDataObserver() {
        viewModel.walletLiveData.observe(viewLifecycleOwner) {
            updateWallet(it)
        }
    }

    private fun updateWallet(wallet: Wallet) {
        binding.tvCurrencyValue.text = wallet.amount.toString()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }
}
