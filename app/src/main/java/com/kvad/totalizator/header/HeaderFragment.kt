package com.kvad.totalizator.header

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kvad.totalizator.databinding.HeaderFragmentBinding

class HeaderFragment: Fragment() {

    private lateinit var binding: HeaderFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HeaderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
