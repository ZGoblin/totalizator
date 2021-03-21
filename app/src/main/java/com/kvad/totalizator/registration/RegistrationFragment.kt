package com.kvad.totalizator.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kvad.totalizator.App
import com.kvad.totalizator.databinding.RegistrationFragmentBinding
import com.kvad.totalizator.registration.models.RawRegisterRequest
import java.util.*
import javax.inject.Inject

class RegistrationFragment: Fragment() {

    @Inject
    lateinit var viewModel: RegistrationViewModel
    private lateinit var binding: RegistrationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegistrationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDi()
        setupMaxDate()
        setupListener()
    }

    private fun setupMaxDate() {
        binding.dpBirthday.maxDate = Date().time
    }

    private fun setupListener() {
        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        binding.apply {
            viewModel.register(
                RawRegisterRequest(
                    email = teLogin.text.toString(),
                    password = tePassword.text.toString(),
                    day = dpBirthday.dayOfMonth,
                    month = dpBirthday.month,
                    year = dpBirthday.year
                )
            )
        }
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }
}
