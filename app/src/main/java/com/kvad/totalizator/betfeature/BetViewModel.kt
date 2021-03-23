package com.kvad.totalizator.betfeature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.EventRepository
import com.kvad.totalizator.data.model.Event
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias BetDetailState = State<Unit, ErrorState>
typealias EventInfoState = State<Event, ErrorState>

class BetViewModel @Inject constructor(
    private val betUseCase: BetUseCase,
    private val eventRepository: EventRepository
) : ViewModel() {

    private var _betDetailLiveData = MutableLiveData<BetDetailState>()
    val betDetailLiveData: LiveData<BetDetailState> = _betDetailLiveData

    private var _eventInfoLiveData = MutableLiveData<EventInfoState>()
    val eventInfoLiveData: LiveData<EventInfoState> = _eventInfoLiveData

    fun uploadData() {
        viewModelScope.launch {
            _eventInfoLiveData.value = State.Loading
            eventRepository.latestEvent.collect {
                it.doOnResult(
                    onSuccess = { event -> _eventInfoLiveData.value = State.Content(event) },
                    onError = { _eventInfoLiveData.value = State.Error(ErrorState.LOADING_ERROR) }
                )
            }
        }
    }

    fun createBet(betToServerModel: BetToServerModel) {
        _betDetailLiveData.value = State.Loading
        viewModelScope.launch {
            betUseCase.bet(betToServerModel).doOnResult(
                onSuccess = { _betDetailLiveData.value = State.Content(it) },
                onNetworkError = {_betDetailLiveData.value = State.Error(ErrorState.LOADING_ERROR)},
                onLoginError = {_betDetailLiveData.value = State.Error(ErrorState.LOGIN_ERROR)}
            )

        }

    }

}
