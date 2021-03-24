package com.kvad.totalizator.betfeature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.betfeature.domain.BetUseCase
import com.kvad.totalizator.betfeature.domain.CoefficientUseCase
import com.kvad.totalizator.betfeature.domain.MapperEventToBetDetailModel
import com.kvad.totalizator.betfeature.model.BetDetail
import com.kvad.totalizator.betfeature.model.BetToServerModel
import com.kvad.totalizator.data.EventRepository
import com.kvad.totalizator.shared.Bet
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.mapSuccess
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias BetLiveDataState = State<Unit, ErrorState>
typealias BetInfoLivaDataState = State<BetDetail, Unit>


class BetViewModel @Inject constructor(
    private val coefficientUseCase: CoefficientUseCase,
    private val betUseCase: BetUseCase,
    private val mapBetToBetDetailModel: MapperEventToBetDetailModel,
    private val eventRepository: EventRepository
) : ViewModel() {

    private var _betLiveData = MutableLiveData<BetLiveDataState>()
    val betLiveData: LiveData<BetLiveDataState> = _betLiveData

    private var _betInfoLiveData = MutableLiveData<BetInfoLivaDataState>()
    val betInfoLiveData: LiveData<BetInfoLivaDataState> = _betInfoLiveData

    lateinit var lastBetDetail: BetDetail
        private set

    fun uploadData() {
        _betInfoLiveData.value = State.Loading
        viewModelScope.launch {
            eventRepository.latestEvent.map { it.mapSuccess(mapBetToBetDetailModel::map) }.collect {
                it.doOnResult(
                    onSuccess = ::doOnSuccessBetInfo,
                    onError = ::doOnErrorBetInfo
                )
            }
        }
    }

    private fun doOnSuccessBetInfo(betModel: BetDetail) {
        lastBetDetail = betModel
        _betInfoLiveData.value = State.Content(betModel)
    }

    private fun doOnErrorBetInfo(error: ApiResultWrapper.Error) {
        _betInfoLiveData.value = State.Error(Unit)
    }

    fun calculate(bet: Bet, current: Float): Float {
        return coefficientUseCase.calculateCoefficient(lastBetDetail, bet, current)
    }

    fun createBet(betToServerModel: BetToServerModel) {
        _betLiveData.value = State.Loading
        viewModelScope.launch {
            betUseCase.bet(betToServerModel).doOnResult(
                onSuccess = ::doOnSuccessDoBet,
                onNetworkError = ::doOnNetworkErrorDoBet,
                onLoginError = ::doOnLoginErrorDoBet
            )
        }
    }

    private fun doOnSuccessDoBet(unit: Unit) {
        _betLiveData.value = State.Content(unit)
    }

    private fun doOnNetworkErrorDoBet(error: ApiResultWrapper.Error) {
        _betLiveData.value = State.Error(error = ErrorState.LOADING_ERROR)
    }

    private fun doOnLoginErrorDoBet(error: ApiResultWrapper.Error) {
        _betLiveData.value = State.Error(error = ErrorState.LOGIN_ERROR)
    }

}
