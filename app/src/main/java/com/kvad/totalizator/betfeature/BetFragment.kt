package com.kvad.totalizator.betfeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kvad.totalizator.betfeature.model.ChoiceModel
import com.kvad.totalizator.databinding.BetFragmentBinding
import com.kvad.totalizator.shared.Bet

class BetFragment : Fragment() {

    private lateinit var binding: BetFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BetFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private val fakeModelFromEvent = ChoiceModel(
        "1",
        Bet.FIRST_PLAYER_WIN,
        "First player",
        "Second player"
    )

    private fun setupListeners() {
        binding.btnBet.setOnClickListener {
            childFragmentManager.beginTransaction()
                .add(BetDialogFragment.newInstance(fakeModelFromEvent), "TAG")
                .commitAllowingStateLoss()
        }
    }

}
