package com.kvad.totalizator.di

import com.kvad.totalizator.MainActivity
import com.kvad.totalizator.betfeature.BetDialogFragment
import com.kvad.totalizator.beting.bethistory.ui.BetHistoryFragment
import com.kvad.totalizator.event.detail.detail.EventDetailFragment
import com.kvad.totalizator.event.feed.EventsFragment
import com.kvad.totalizator.header.HeaderFragment
import com.kvad.totalizator.login.LoginFragment
import com.kvad.totalizator.onboard.OnBoardFragment
import com.kvad.totalizator.profile.ProfileFragment
import com.kvad.totalizator.registration.RegistrationFragment
import com.kvad.totalizator.accaunt.transaction.deposit.DepositPageFragment
import com.kvad.totalizator.accaunt.transaction.TransactionPagerFragment
import com.kvad.totalizator.accaunt.transaction.withdraw.WithdrawPageFragment
import dagger.Component
import javax.inject.Singleton

@Suppress("TooManyFunctions")
@Singleton
@Component(
    dependencies = [],
    modules = [AppModule::class, DispatcherModule::class, ViewModelModule::class, NetworkModule::class]
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

    fun inject(fragment: TransactionPagerFragment)

    fun inject(fragment: WithdrawPageFragment)

    fun inject(fragment: DepositPageFragment)

    fun inject(fragment : BetHistoryFragment)

    fun inject(fragment : ProfileFragment)

}
