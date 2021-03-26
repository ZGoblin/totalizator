package com.kvad.totalizator.bethistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kvad.totalizator.App
import com.kvad.totalizator.betfeature.domain.BetState
import com.kvad.totalizator.databinding.BetHistoryFragmentBinding
import com.kvad.totalizator.tools.State
import kotlinx.coroutines.cancel
import javax.inject.Inject

class BetHistoryFragment : Fragment() {

    private var _binding: BetHistoryFragmentBinding? = null
    private val binding get() = _binding!!
    private val betHistoryAdapter = BetHistoryAdapter()

    @Inject
    lateinit var viewModel: BetHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BetHistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDi()
        setupRecyclerView()
        setupLiveDataObserver()
        setupListeners()
    }

    private fun setupLiveDataObserver() {
        viewModel.betHistoryLiveData.observe(viewLifecycleOwner) {
            updateBetHistory(it)
        }
    }

    private fun updateBetHistory(state: State<List<BetHistoryDetailModel>, BetState>?) {
        when (state) {
            is State.Content -> betHistoryAdapter.submitList(state.data)
            is State.Error -> {
            }
            is State.Loading -> {
            }
        }
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
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
        super.onDestroyView()
    }

}