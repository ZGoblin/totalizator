package com.kvad.totalizator.account.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.account.data.model.LoginRequest
import com.kvad.totalizator.databinding.LoginFragmentBinding
import com.kvad.totalizator.di.ViewModelFactory
import com.kvad.totalizator.di.injectViewModel
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: LoginViewModel
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDi()
        setupListener()
        setupLiveDataObserver()
    }

    private fun setupLiveDataObserver() {
        viewModel.loginStateViewModel.observe(viewLifecycleOwner) {
            updateState(it)
        }
    }

    private fun updateState(state: LoginState) {
        hideInputError()
        when (state) {
            LoginState.WITHOUT_ERROR -> {
                findNavController().navigate(R.id.action_to_event_from_login)
            }
            LoginState.NETWORK_ERROR -> {
                showNetworkErrorDialog(R.string.login_network_error_dialog_body)
            }
            LoginState.EMAIL_LENGTH_ERROR -> {
                binding.tfLogin.error = getString(R.string.login_login_error)
            }
            LoginState.PASSWORD_LENGTH_ERROR -> {
                binding.tfPassword.error = getString(R.string.login_password_error)
            }
            LoginState.EMAIL_DOG_NOT_INCLUDE -> {
                binding.tfLogin.error = getString(R.string.login_username_symbol_error)
            }
        }
    }

    private fun hideInputError() {
        binding.apply {
            tfLogin.error = null
            tfPassword.error = null
        }
    }

    private fun showNetworkErrorDialog(@StringRes text: Int) {
        MaterialDialog(requireContext()).show {
            message(text)
            positiveButton(R.string.login_network_error_dialog_button)
        }
    }

    private fun setupListener() {
        binding.apply {
            btnLogin.setOnClickListener {
                login()
            }
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_to_registration)
            }
        }
    }

    fun login() {
        binding.apply {
            viewModel.login(
                LoginRequest(
                    login = teLogin.text.toString(),
                    password = tePassword.text.toString()
                )
            )
        }
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
        viewModel = injectViewModel(viewModelFactory)
    }

    override fun onDestroyView() {
        binding.root.layoutAnimation = null
        _binding = null
        super.onDestroyView()
    }
}
