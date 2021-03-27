package com.kvad.totalizator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.kvad.totalizator.di.ViewModelFactory
import com.kvad.totalizator.di.injectViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupDi()
        observeViewModel()
        supportActionBar?.hide()
    }

    private fun observeViewModel(){
        viewModel.openNavigatorLiveData.observe(this){
            navigate(it)
        }
    }

    private fun navigate(openNavigator: OpenNavigator) {
        when(openNavigator){
            OpenNavigator.FIRST_APP_OPEN -> Navigation.findNavController(this, R.id.fcvBody).navigate(R.id.on_board_fragment)
        }
    }

    private fun setupDi() {
        val app = application as App
        app.getComponent().inject(this)
        viewModel = injectViewModel(viewModelFactory)
    }
}
