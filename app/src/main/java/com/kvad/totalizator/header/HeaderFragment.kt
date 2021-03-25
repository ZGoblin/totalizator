package com.kvad.totalizator.header

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.data.requestmodels.Wallet
import com.kvad.totalizator.databinding.HeaderFragmentBinding
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import javax.inject.Inject

class HeaderFragment : Fragment() {

    @Inject
    lateinit var viewModel: HeaderViewModel
    private var _binding: HeaderFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HeaderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDi()
        setupListener()
        setupLiveDataObserver()

        showLoginButton(false)
        viewModel.getWallet()
    }

    private fun setupListener() {
        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.login_fragment)
        }
    }

    private fun setupLiveDataObserver() {
        viewModel.headerLiveData.observe(viewLifecycleOwner) {
            updateState(it)
        }
    }

    private fun updateState(state: State<Wallet, ErrorState>) {
        when (state) {
            is State.Content -> {
                showLoginButton(false)
                updateWallet(state.data)
            }
            is State.Error -> showLoginButton(true)
            else -> {}
        }
    }

    private fun showLoginButton(show: Boolean) {
        binding.apply {
            if (show) {
                tvLogin.visibility = View.VISIBLE
                tvCurrency.visibility = View.GONE
                tvCurrencyValue.visibility = View.GONE
                ivAvatar.visibility = View.GONE
            } else {
                tvLogin.visibility = View.GONE
                tvCurrency.visibility = View.VISIBLE
                tvCurrencyValue.visibility = View.VISIBLE
                ivAvatar.visibility = View.VISIBLE
            }
        }
    }

    private fun updateWallet(wallet: Wallet) {
        binding.tvCurrencyValue.text = wallet.amount.toString()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
