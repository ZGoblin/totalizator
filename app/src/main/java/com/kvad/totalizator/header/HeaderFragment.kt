package com.kvad.totalizator.header

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.accaunt.data.model.AccountInfo
import com.kvad.totalizator.accaunt.data.model.Wallet
import com.kvad.totalizator.databinding.HeaderFragmentBinding
import com.kvad.totalizator.di.ViewModelFactory
import com.kvad.totalizator.di.injectViewModel
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import java.math.RoundingMode
import javax.inject.Inject

@Suppress("TooManyFunctions")
class HeaderFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
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
        hideAll()
    }

    private fun setupListener() {
        binding.apply {
            tvLogin.setOnClickListener {
                findNavController().navigate(R.id.action_to_login)
            }
            tvCurrencyValue.setOnClickListener {
                navigateToTransaction()
            }
            tvCurrency.setOnClickListener {
                navigateToTransaction()
            }
            ivClock.setOnClickListener {
                findNavController().navigate(R.id.bet_history_fragment)
            }
            ivAvatar.setOnClickListener {
                findNavController().navigate(R.id.action_to_profile)
            }
        }
    }

    private fun navigateToTransaction() {
        findNavController().navigate(R.id.action_to_transaction)
    }

    private fun setupLiveDataObserver() {
        viewModel.walletLiveData.observe(viewLifecycleOwner) {
            updateWalletState(it)
        }
        viewModel.accountLiveData.observe(viewLifecycleOwner) {
            updateAccountState(it)
        }
    }

    private fun updateAccountState(state: State<AccountInfo, ErrorState>) {
        when (state) {
            is State.Content -> {
                updateAvatar(state.data)
            }
            is State.Error -> {
                showError(state)
            }
            else -> {}
        }
    }

    private fun showError(state: State.Error<ErrorState>) {
        when (state.error) {
            ErrorState.LOGIN_ERROR -> showLoginButton()
            ErrorState.LOADING_ERROR -> hideAll()
        }
    }

    private fun updateWalletState(state: State<Wallet, ErrorState>) {
        when (state) {
            is State.Content -> {
                showUser()
                updateWallet(state.data)
            }
            is State.Error -> {
                showError(state)
            }
            else -> {}
        }
    }

    private fun hideAll() {
        binding.apply {
            tvLogin.visibility = View.GONE
            tvCurrency.visibility = View.GONE
            tvCurrencyValue.visibility = View.GONE
            ivAvatar.visibility = View.GONE
            ivClock.visibility = View.GONE
        }
    }

    private fun showLoginButton() {
        binding.apply {
            tvLogin.visibility = View.VISIBLE
            tvCurrency.visibility = View.GONE
            tvCurrencyValue.visibility = View.GONE
            ivAvatar.visibility = View.GONE
            ivClock.visibility = View.GONE
        }
    }

    private fun showUser() {
        binding.apply {
            tvLogin.visibility = View.GONE
            tvCurrency.visibility = View.VISIBLE
            tvCurrencyValue.visibility = View.VISIBLE
            ivAvatar.visibility = View.VISIBLE
            ivClock.visibility = View.VISIBLE
        }
    }

    private fun updateWallet(wallet: Wallet) {
        val currencyValue = wallet.amount.toBigDecimal().setScale(2, RoundingMode.DOWN).toString()
        binding.tvCurrencyValue.text = currencyValue
    }

    private fun updateAvatar(accountInfo: AccountInfo) {
        Glide.with(this)
            .load(accountInfo.avatarLink)
            .error(R.drawable.ic_user_avatar)
            .into(binding.ivAvatar)
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
        viewModel = injectViewModel(viewModelFactory)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
