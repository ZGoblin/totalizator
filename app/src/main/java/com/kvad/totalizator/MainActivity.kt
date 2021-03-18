package com.kvad.totalizator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kvad.totalizator.databinding.ActivityMainBinding
import com.kvad.totalizator.header.HeaderFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        //delete late
        supportFragmentManager.beginTransaction()
            .add(binding.fcvHeader.id, HeaderFragment())
            .commit()
    }
}
