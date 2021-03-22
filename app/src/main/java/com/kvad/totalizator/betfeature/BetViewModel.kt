package com.kvad.totalizator.betfeature

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.BetRepository
import com.kvad.totalizator.data.EventRepository
import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias BetDetailState = State<Unit, ErrorState>
typealias EventInfoState = State<Event, Unit>

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
            eventRepository.latestEvent.collect { updateUiState(it) }
        }
    }

    private fun updateUiState(resultWrapper: ResultWrapper<Event>) {
        _eventInfoLiveData.value =
            when (resultWrapper) {
                is ResultWrapper.Success -> State.Content(resultWrapper.value)
                is ResultWrapper.DataLoadingError -> TODO()
                is ResultWrapper.LoginError -> TODO()
            }
    }
    //modelka
    fun createBet(eventId: String, amount: Double, bet: Bet) {
        val betToServerModel = BetToServerModel(eventId, amount, bet)
        _betDetailLiveData.postValue(State.Loading)
        viewModelScope.launch {
            _betDetailLiveData.postValue(
                when (betUseCase.bet(betToServerModel)) {
                    is ResultWrapper.Success -> State.Content(Unit)
                    is ResultWrapper.DataLoadingError -> State.Error(ErrorState.LOADING_ERROR)
                    is ResultWrapper.LoginError -> State.Error(ErrorState.LOGIN_ERROR)
                }
            )
        }
    }

}
