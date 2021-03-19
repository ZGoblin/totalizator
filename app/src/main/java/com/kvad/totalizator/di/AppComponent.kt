package com.kvad.totalizator.di

import com.kvad.totalizator.MainActivity
import com.kvad.totalizator.header.HeaderFragment
import com.kvad.totalizator.onboard.OnBoardFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: HeaderFragment)

    fun inject(fragment: OnBoardFragment)
}
