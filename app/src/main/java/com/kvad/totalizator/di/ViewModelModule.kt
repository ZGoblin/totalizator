package com.kvad.totalizator.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kvad.totalizator.NavigationViewModel
import com.kvad.totalizator.beting.betfeature.BetViewModel
import com.kvad.totalizator.beting.bethistory.ui.BetHistoryViewModel
import com.kvad.totalizator.chat.ui.ChatViewModel
import com.kvad.totalizator.event.detail.EventDetailViewModel
import com.kvad.totalizator.event.feed.EventsViewModel
import com.kvad.totalizator.header.HeaderViewModel
import com.kvad.totalizator.account.login.LoginViewModel
import com.kvad.totalizator.profile.ProfileViewModel
import com.kvad.totalizator.account.registration.RegistrationViewModel
import com.kvad.totalizator.account.transaction.deposit.DepositViewModel
import com.kvad.totalizator.account.transaction.withdraw.WithdrawViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("TooManyFunctions")
@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(NavigationViewModel::class)
    internal abstract fun navigationViewModel(viewModel: NavigationViewModel): ViewModel

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

    @Binds
    @IntoMap
    @ViewModelKey(BetHistoryViewModel::class)
    internal abstract fun betHistoryViewModel(viewModel: BetHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun profileViewModel(viewModel: ProfileViewModel): ViewModel
}
