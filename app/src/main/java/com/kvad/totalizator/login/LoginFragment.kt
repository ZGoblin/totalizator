package com.kvad.totalizator.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.data.requestmodels.LoginRequest
import com.kvad.totalizator.databinding.LoginFragmentBinding
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
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
                findNavController().navigate(R.id.events_fragment)
            }
            LoginState.NETWORK_ERROR -> {
                showNetworkErrorDialog()
            }
            LoginState.LOGIN_LENGTH_ERROR -> {
                binding.tfLogin.error = getString(R.string.login_login_error)
            }
            LoginState.PASSWORD_LENGTH_ERROR -> {
                binding.tfPassword.error = getString(R.string.login_password_error)
            }
        }
    }

    private fun hideInputError() {
        binding.apply {
            tfLogin.error = null
            tfPassword.error = null
        }
    }

    private fun showNetworkErrorDialog() {
        MaterialDialog(requireContext()).show {
            message(R.string.login_network_error_dialog_body)
            positiveButton(R.string.login_network_error_dialog_button)
        }
    }

    private fun setupListener() {
        binding.apply {
            btnLogin.setOnClickListener {
                login()
            }
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.registration_fragment)
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
    }
}
