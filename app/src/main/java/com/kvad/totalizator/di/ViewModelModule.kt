package com.kvad.totalizator.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kvad.totalizator.betfeature.BetViewModel
import com.kvad.totalizator.chat.ui.ChatViewModel
import com.kvad.totalizator.detail.EventDetailViewModel
import com.kvad.totalizator.events.EventsViewModel
import com.kvad.totalizator.header.HeaderViewModel
import com.kvad.totalizator.login.LoginViewModel
import com.kvad.totalizator.registration.RegistrationViewModel
import com.kvad.totalizator.transactionfeature.deposit.DepositViewModel
import com.kvad.totalizator.transactionfeature.withdraw.WithdrawViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(
        factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    internal abstract fun chatViewModel(viewModel: ChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EventDetailViewModel::class)
    internal abstract fun detailEventVieModel(viewModel: EventDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HeaderViewModel::class)
    internal abstract fun headerViewModel(viewModel: HeaderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EventsViewModel::class)
    internal abstract fun eventsViewModel(viewModel: EventsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BetViewModel::class)
    internal abstract fun betViewModel(viewModel: BetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun loginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    internal abstract fun registrationViewModel(viewModel: RegistrationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DepositViewModel::class)
    internal abstract fun depositVieModel(viewModel: DepositViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WithdrawViewModel::class)
    internal abstract fun withdrawViewModel(viewModel: WithdrawViewModel): ViewModel
}
