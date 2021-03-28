package com.kvad.totalizator.beting.bethistory.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.beting.data.BetRepository
import com.kvad.totalizator.beting.quickbet.domain.BetState
import com.kvad.totalizator.beting.bethistory.model.BetHistoryDetailModel
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias BetHistoryState = State<List<BetHistoryDetailModel>, BetState>

class BetHistoryViewModel @Inject constructor(
    private val betRepository: BetRepository
) : ViewModel() {

    private val _betHistoryLiveData = MutableLiveData<BetHistoryState>()
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
        Log.d("ErrorBody",error.msg)
        _betHistoryLiveData.value = State.Error(BetState.LOADING_ERROR)
    }

}

