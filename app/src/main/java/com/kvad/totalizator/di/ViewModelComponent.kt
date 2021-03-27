package com.kvad.totalizator.di

import com.kvad.totalizator.betfeature.BetViewModel
import com.kvad.totalizator.bethistory.ui.BetHistoryViewModel
import com.kvad.totalizator.chat.ui.ChatViewModel
import com.kvad.totalizator.detail.EventDetailViewModel
import com.kvad.totalizator.events.EventsViewModel
import com.kvad.totalizator.header.HeaderViewModel
import com.kvad.totalizator.login.LoginViewModel
import com.kvad.totalizator.registration.RegistrationViewModel
import com.kvad.totalizator.transactionfeature.deposit.DepositViewModel
import com.kvad.totalizator.transactionfeature.withdraw.WithdrawViewModel
import dagger.Component

@Component(modules = [ViewModelModule::class])
interface ViewModelComponent {

    fun inject(viewModel: ChatViewModel)

    fun inject(viewModel: EventDetailViewModel)

    fun inject(viewModel: HeaderViewModel)

    fun inject(viewModel: EventsViewModel)

    fun inject(viewModel: BetViewModel)

    fun inject(viewModel: LoginViewModel)

    fun inject(viewModel: RegistrationViewModel)

    fun inject(viewModel: DepositViewModel)

    fun inject(viewModel: WithdrawViewModel)

    fun inject(viewModel: BetHistoryViewModel)
}
