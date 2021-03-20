package com.kvad.totalizator.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kvad.totalizator.App
import com.kvad.totalizator.data.models.Login
import com.kvad.totalizator.databinding.LoginFragmentBinding
import javax.inject.Inject
import kotlin.math.log

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

    }

    private fun setupListener() {
        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    fun login() {
        viewModel.login(
            Login(
                login = binding.teLogin.text.toString(),
                password = binding.tePassword.text.toString()
            )
        )
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }
}
