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
import com.kvad.totalizator.tools.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

typealias BetDetailState = State<Unit, IOException>

class BetViewModel @Inject constructor(
    //todo
    private val betRepository: BetRepository
) : ViewModel() {

    private val _betDetailLiveData = MutableLiveData<BetDetailState>()
    val betDetailLiveData: LiveData<BetDetailState> = _betDetailLiveData
    private var eventId: String = ""
    private lateinit var bet: Bet
    //TODO
//    fun sendBet(betAmount: Int) {
//        /*
//        betAmount : Int,
//        -go to repository for request to backend
//        -when state
//         */
//    }

    fun setupData(eventId: String, bet: Bet) {
        this.eventId = eventId
        this.bet = bet
    }

    fun createBet(amount: Int) {

        val betToServerModel = BetToServerModel(eventId, amount, bet)
        _betDetailLiveData.postValue(State.Loading)
        viewModelScope.launch {
            when (val result = betRepository.doBet(betToServerModel)){
                is ResultWrapper.Success -> _betDetailLiveData.value = State.Content(Unit)
                //is ResultWrapper.DataLoadingError -> _betDetailLiveData.value = State.Error()
        }
        }
    }

}
