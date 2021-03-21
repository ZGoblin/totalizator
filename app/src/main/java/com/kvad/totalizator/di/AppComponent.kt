package com.kvad.totalizator.di

import com.kvad.totalizator.MainActivity
import com.kvad.totalizator.betfeature.BetDialogFragment
import com.kvad.totalizator.detail.EventDetailFragment
import com.kvad.totalizator.events.EventsFragment
import com.kvad.totalizator.header.HeaderFragment
import com.kvad.totalizator.onboard.OnBoardFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: HeaderFragment)

    fun inject(fragment: EventsFragment)
  
    fun inject(fragment: OnBoardFragment)

    fun inject(fragment: EventDetailFragment)

    fun inject(fragment: BetDialogFragment)
}
