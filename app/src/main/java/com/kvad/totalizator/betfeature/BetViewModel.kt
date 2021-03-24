package com.kvad.totalizator.betfeature

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.EventRepository
import com.kvad.totalizator.data.model.Event
import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.mapSuccess
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias BetDetailState = State<Unit, ErrorState>
typealias EventInfoState = State<BetDetail, Unit>


class BetViewModel @Inject constructor(
    private val coefficientUseCase: CoefficientUseCase,
    private val betUseCase: BetUseCase,
    private val mapBetToBetDetailModel : MapperEventToBetDetailModel,
    private val eventRepository: EventRepository
) : ViewModel() {

    private var _betDetailLiveData = MutableLiveData<BetDetailState>()
    val betDetailLiveData: LiveData<BetDetailState> = _betDetailLiveData

    private var _eventInfoLiveData = MutableLiveData<EventInfoState>()
    val eventInfoLiveData: LiveData<EventInfoState> = _eventInfoLiveData

    lateinit var lastBetDetail : BetDetail
        private set

    fun uploadData() {
        _eventInfoLiveData.value = State.Loading
        viewModelScope.launch {
            eventRepository.latestEvent.map{it.mapSuccess ( mapBetToBetDetailModel::map )}.collect {
                it.doOnResult(
                        onSuccess = ::doOnSuccess,
                        onError = ::doOnError
                    )
                }
            }
        }

    private fun doOnSuccess(betModel : BetDetail){
        lastBetDetail = betModel
        _eventInfoLiveData.value = State.Content(betModel)
    }

    private fun doOnError(error: ApiResultWrapper.Error){
        _eventInfoLiveData.value = State.Error(Unit)
    }

    fun calculate(bet : Bet, current : Float): Float {
        return coefficientUseCase.calculateCoefficient(lastBetDetail,bet,current)
    }

    fun createBet(betToServerModel: BetToServerModel) {
        _betDetailLiveData.value = State.Loading
        viewModelScope.launch {
            betUseCase.bet(betToServerModel).doOnResult(
                onSuccess = { _betDetailLiveData.value = State.Content(it) },
                onNetworkError = {
                    _betDetailLiveData.value = State.Error(ErrorState.LOADING_ERROR)
                },
                onLoginError = { _betDetailLiveData.value = State.Error(ErrorState.LOGIN_ERROR) }
            )
        }

    }

}
