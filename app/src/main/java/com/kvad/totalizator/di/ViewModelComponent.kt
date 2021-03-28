package com.kvad.totalizator.di

import com.kvad.totalizator.NavigationViewModel
import com.kvad.totalizator.beting.betfeature.BetViewModel
import com.kvad.totalizator.beting.bethistory.ui.BetHistoryViewModel
import com.kvad.totalizator.chat.ui.ChatViewModel
import com.kvad.totalizator.event.detail.EventDetailViewModel
import com.kvad.totalizator.event.feed.EventsViewModel
import com.kvad.totalizator.header.HeaderViewModel
import com.kvad.totalizator.accaunt.login.LoginViewModel
import com.kvad.totalizator.profile.ProfileViewModel
import com.kvad.totalizator.accaunt.registration.RegistrationViewModel
import com.kvad.totalizator.accaunt.transaction.deposit.DepositViewModel
import com.kvad.totalizator.accaunt.transaction.withdraw.WithdrawViewModel
import dagger.Component
@Suppress("TooManyFunctions")
@Component(modules = [ViewModelModule::class])
interface ViewModelComponent {

    fun inject(viewModel: ChatViewModel)

    fun inject( viewModel: NavigationViewModel )

    fun inject(viewModel: EventDetailViewModel)

    fun inject(viewModel: HeaderViewModel)

    fun inject(viewModel: EventsViewModel)

    fun inject(viewModel: BetViewModel)

    fun inject(viewModel: LoginViewModel)

    fun inject(viewModel: RegistrationViewModel)

    fun inject(viewModel: DepositViewModel)

    fun inject(viewModel: WithdrawViewModel)

    fun inject(viewModel: BetHistoryViewModel)

    fun inject(viewModel: ProfileViewModel)
}
