package com.kvad.totalizator.betfeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kvad.totalizator.databinding.BetFragmentBinding

class BetFragment : Fragment() {

    private lateinit var  binding : BetFragmentBinding

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
    val model = ChoiceModel(ChoiceState.FIRST,
        CommandInfoSum("Connor",300.0),CommandInfoSum("Petia",400.0))
    private fun setupListeners(){
        binding.btnBet.setOnClickListener {

            childFragmentManager.beginTransaction()
                .add(BetDialogFragment.newInstance(model),"TAG")
                .commitAllowingStateLoss()
        }
    }

}