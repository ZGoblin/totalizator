package com.kvad.totalizator.di

import androidx.lifecycle.ViewModel
import com.kvad.totalizator.MainActivity
import com.kvad.totalizator.betfeature.BetDialogFragment
import com.kvad.totalizator.chat.ui.ChatFragment
import com.kvad.totalizator.detail.EventDetailFragment
import com.kvad.totalizator.events.EventsFragment
import com.kvad.totalizator.header.HeaderFragment
import com.kvad.totalizator.login.LoginFragment
import com.kvad.totalizator.onboard.OnBoardFragment
import com.kvad.totalizator.registration.RegistrationFragment
import com.kvad.totalizator.transactionfeature.deposit.DepositPageFragment
import com.kvad.totalizator.transactionfeature.TransactionPagerFragment
import com.kvad.totalizator.transactionfeature.withdraw.WithdrawPageFragment
import dagger.Component
import dagger.MapKey
import javax.inject.Singleton
import kotlin.reflect.KClass


@Suppress("TooManyFunctions")
@Singleton
@Component(
    dependencies = [],
    modules = [AppModule::class, DispatcherModule::class, ViewModelModule::class]
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

    fun inject(fragment: ChatFragment)

    fun inject(fragment: TransactionPagerFragment)

    fun inject(fragment: WithdrawPageFragment)

    fun inject(fragment: DepositPageFragment)

}
