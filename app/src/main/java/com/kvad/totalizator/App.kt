package com.kvad.totalizator

import android.app.Application
import com.kvad.totalizator.di.AppComponent
import com.kvad.totalizator.di.AppModule
import com.kvad.totalizator.di.DaggerAppComponent

class App : Application() {

    private lateinit var daggerComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        daggerComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun getComponent(): AppComponent {
        return daggerComponent
    }
}
