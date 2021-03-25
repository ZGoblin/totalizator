package com.kvad.totalizator.di

import com.kvad.totalizator.MainActivity
import com.kvad.totalizator.betfeature.BetDialogFragment
import com.kvad.totalizator.chat.ui.ChatFragment
import com.kvad.totalizator.detail.EventDetailFragment
import com.kvad.totalizator.events.EventsFragment
import com.kvad.totalizator.header.HeaderFragment
import com.kvad.totalizator.login.LoginFragment
import com.kvad.totalizator.onboard.OnBoardFragment
import com.kvad.totalizator.registration.RegistrationFragment
import com.kvad.totalizator.transactionfeature.TransactionFragment
import com.kvad.totalizator.transactionfeature.TransactionPagerFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [],
    modules = [AppModule::class, DispatcherModule::class]
)
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: HeaderFragment)

    fun inject(fragment: EventsFragment)
  
    fun inject(fragment: OnBoardFragment)

    fun inject(fragment: EventDetailFragment)

    fun inject(fragment: BetDialogFragment)

    fun inject(fragment: LoginFragment)

    fun inject(fragment: RegistrationFragment)

    fun inject(fragment: TransactionFragment)

    fun inject(fragment: ChatFragment)

    fun inject(fragment : TransactionPagerFragment)

}
