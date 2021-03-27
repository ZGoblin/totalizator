package com.kvad.totalizator.betfeature

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.betfeature.domain.BetState
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

typealias BetLiveDataState = State<Unit, BetState>
typealias BetInfoLivaDataState = State<BetDetail, ErrorState>


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

    fun uploadData(id: String) {
        _betInfoLiveData.value = State.Loading
        viewModelScope.launch {
            eventRepository.getEventById(id).map { it.mapSuccess(mapBetToBetDetailModel::map) }
                .collect {
                    it.doOnResult(
                        onSuccess = ::doOnSuccessBetInfo,
                        onError = ::doOnErrorBetInfo
                    )
                }
        }
    }

    fun calculate(bet: Bet, current: Float): Float {
        return coefficientUseCase.calculateCoefficient(lastBetDetail, bet, current)
    }

    private fun doOnSuccessBetInfo(betModel: BetDetail) {
        lastBetDetail = betModel
        if (_betLiveData.value !is State.Loading) {
            _betInfoLiveData.value = State.Content(betModel)
        }
    }

    private fun doOnErrorBetInfo(error: ApiResultWrapper.Error) {
        Log.d("ErrorBody", error.msg)
        _betInfoLiveData.value = State.Error(ErrorState.LOADING_ERROR)
    }

    fun createBet(betToServerModel: BetToServerModel) {
        _betLiveData.value = State.Loading
        viewModelScope.launch {
            betUseCase.doBet(betToServerModel).doOnResult(
                onSuccess = ::doOnSuccessDoBet,
                onNetworkError = ::doOnNetworkErrorDoBet,
                onLoginError = ::doOnLoginErrorDoBet,
                onNoMoneyError = ::doOnNoMoneyError
            )
        }
    }

    private fun doOnSuccessDoBet(unit: Unit) {
        _betLiveData.value = State.Content(unit)
    }

    private fun doOnNoMoneyError(error: ApiResultWrapper.Error.NoMoneyError) {
        Log.d("ErrorBody", error.msg)
        _betLiveData.value = State.Error(error = BetState.NO_MONEY_LEFT)
    }

    private fun doOnNetworkErrorDoBet(error: ApiResultWrapper.Error.NetworkError) {
        Log.d("ErrorBody", error.msg)
        _betLiveData.value = State.Error(error = BetState.LOADING_ERROR)
    }

    private fun doOnLoginErrorDoBet(error: ApiResultWrapper.Error.LoginError) {
        Log.d("ErrorBody", error.msg)
            _betLiveData.value = State.Error(error = BetState.LOGIN_ERROR)
    }

}
