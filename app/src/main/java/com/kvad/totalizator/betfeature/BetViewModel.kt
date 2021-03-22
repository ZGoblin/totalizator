package com.kvad.totalizator.betfeature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.betfeature.model.BetDetailModel
import com.kvad.totalizator.betfeature.model.ChoiceModel
import com.kvad.totalizator.data.BetRepository
import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

typealias BetDetailState = State<Unit, ErrorState>

class BetViewModel @Inject constructor(
    private val betRepository: BetRepository
) : ViewModel() {

    private val _betDetailLiveData = MutableLiveData<BetDetailState>()
    val betDetailLiveData: LiveData<BetDetailState> = _betDetailLiveData

    private var eventId: String = ""
    private lateinit var bet: Bet

    fun setupData(eventId: String, bet: Bet) {
        this.eventId = eventId
        this.bet = bet
    }

    fun createBet(amount: Double) {
        val betToServerModel = BetToServerModel(eventId, amount, bet)
        _betDetailLiveData.postValue(State.Loading)
        viewModelScope.launch {
            _betDetailLiveData.value = when (betRepository.doBet(betToServerModel)) {
                is ResultWrapper.Success -> State.Content(Unit)
                is ResultWrapper.DataLoadingError -> State.Error(ErrorState.LOADING_ERROR)
                is ResultWrapper.LoginError -> State.Error(ErrorState.LOGIN_ERROR)
            }
        }
    }
}
