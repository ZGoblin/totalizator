package com.kvad.totalizator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.kvad.totalizator.databinding.ActivityMainBinding
import com.kvad.totalizator.tools.sharedPrefTools.SharedPref
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupDi()
        supportActionBar?.hide()

    }

    override fun onStart() {
        super.onStart()
        isFirstOpen()
    }

    private fun isFirstOpen() {
        if (sharedPref.isFirstOpened) {
            sharedPref.isFirstOpened = false
            Navigation.findNavController(this, R.id.fcvBody).navigate(R.id.on_board_fragment)
        }
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupDi() {
        val app = application as App
        app.getComponent().inject(this)
    }
}
