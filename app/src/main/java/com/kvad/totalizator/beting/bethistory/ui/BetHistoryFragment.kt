package com.kvad.totalizator.beting.bethistory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.kvad.totalizator.App
import com.kvad.totalizator.beting.betfeature.domain.BetState
import com.kvad.totalizator.beting.bethistory.adapter.BetHistoryAdapter
import com.kvad.totalizator.beting.bethistory.model.BetHistoryDetailModel
import com.kvad.totalizator.databinding.BetHistoryFragmentBinding
import com.kvad.totalizator.di.ViewModelFactory
import com.kvad.totalizator.di.injectViewModel
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.StateVisibilityController
import javax.inject.Inject

class BetHistoryFragment : Fragment() {

    private var _binding: BetHistoryFragmentBinding? = null
    private val binding get() = _binding!!
    private val betHistoryAdapter = BetHistoryAdapter()
    private lateinit var stateVisibilityController: StateVisibilityController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: BetHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BetHistoryFragmentBinding.inflate(inflater, container, false)
        stateVisibilityController = StateVisibilityController(binding.progressBarHistory, null)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDi()
        setupRecyclerView()
        setupLiveDataObserver()
        setupListeners()
        viewModel.updateHistory()
    }

    private fun setupLiveDataObserver() {
        viewModel.betHistoryLiveData.observe(viewLifecycleOwner) {
            updateBetHistory(it)
        }
    }

    private fun updateBetHistory(state: State<List<BetHistoryDetailModel>, BetState>?) {
        when (state) {
            is State.Content -> {
                stateVisibilityController.hideLoading()
                betHistoryAdapter.submitList(state.data)
            }
            is State.Error -> {
                MaterialDialog(requireContext()).customView(R.layout.something_went_wrong_layout)
                    .negativeButton(R.string.close).show()
                stateVisibilityController.hideLoading()
            }
            is State.Loading -> stateVisibilityController.showLoading()
        }
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
        viewModel = injectViewModel(viewModelFactory)
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {
        binding.rvBetHistory.adapter = betHistoryAdapter
        binding.rvBetHistory.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onDestroyView() {
        binding.rvBetHistory.adapter = null
        _binding = null
        stateVisibilityController.destroy()
        super.onDestroyView()
    }

}
