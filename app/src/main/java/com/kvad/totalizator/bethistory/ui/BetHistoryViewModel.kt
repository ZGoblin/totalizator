package com.kvad.totalizator.bethistory.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.betfeature.BetRepository
import com.kvad.totalizator.betfeature.domain.BetState
import com.kvad.totalizator.bethistory.model.BetHistoryDetailModel
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import kotlinx.coroutines.launch
import javax.inject.Inject

class BetHistoryViewModel @Inject constructor(
    private val betRepository: BetRepository
) : ViewModel() {

    private val _betHistoryLiveData = MutableLiveData<State<List<BetHistoryDetailModel>, BetState>>()
    val betHistoryLiveData = _betHistoryLiveData

    fun updateHistory(){
        _betHistoryLiveData.value = State.Loading
        viewModelScope.launch {
            betRepository.getBetHistory().doOnResult(
                onSuccess = ::doOnSuccess,
                onError = ::doOnError
                    )
        }
    }

    private fun doOnSuccess(list: List<BetHistoryDetailModel>) {
        _betHistoryLiveData.value = State.Content(list)
    }

    private fun doOnError(error: ApiResultWrapper.Error) {
        _betHistoryLiveData.value = State.Error(BetState.LOADING_ERROR)
    }

}
