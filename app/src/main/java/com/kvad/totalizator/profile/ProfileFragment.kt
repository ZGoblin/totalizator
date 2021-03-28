package com.kvad.totalizator.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.kvad.totalizator.App
import com.kvad.totalizator.R
import com.kvad.totalizator.account.data.model.AccountInfo
import com.kvad.totalizator.databinding.ProfileFragmentBinding
import com.kvad.totalizator.di.ViewModelFactory
import com.kvad.totalizator.di.injectViewModel
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.StateVisibilityController
import javax.inject.Inject

class ProfileFragment: Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: ProfileViewModel
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var stateVisibilityController: StateVisibilityController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        stateVisibilityController = StateVisibilityController(binding.pbProgress, binding.tvError)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDi()
        setupListener()
        setupLiveDataObserver()
        viewModel.updateAccountInfo()
    }

    private fun setupLiveDataObserver() {
        viewModel.accountLiveData.observe(viewLifecycleOwner) {
            updateAccountInfo(it)
        }
    }

    private fun updateAccountInfo(state: AccountState) {
        stateVisibilityController.hideAll()
        when (state) {
            is State.Content -> setAccountInfo(state.data)
            is State.Error -> stateVisibilityController.showError()
            is State.Loading -> stateVisibilityController.showLoading()
        }
    }

    private fun setAccountInfo(info: AccountInfo) {
        binding.apply {
            tvUsername.text = info.username
            Glide.with(ivAvatar)
                .load(info.avatarLink)
                .error(R.drawable.ic_user_avatar)
                .into(binding.ivAvatar)
        }
    }

    private fun setupListener() {
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        viewModel.logout()
        findNavController().navigate(R.id.action_to_event_from_login)
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
