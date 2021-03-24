package com.kvad.totalizator.transactionfeature

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.transactionfeature.domain.TransactionUseCase
import com.kvad.totalizator.transactionfeature.model.TransactionModel
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias transactionStateLiveData = State<Unit, TransactionState>

class TransactionViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase
) : ViewModel() {

    private var _transactionLiveData = MutableLiveData<transactionStateLiveData>()
    val transactionLiveData: LiveData<transactionStateLiveData> = _transactionLiveData

    fun doTransaction(transactionModel: TransactionModel) {
        _transactionLiveData.value = State.Loading
        viewModelScope.launch {
            transactionUseCase.deposit(transactionModel).doOnResult(
                onSuccess = ::doOnSuccess,
                onLoginError = ::doOnLoginError,
                onNetworkError = ::doOnNetworkError,
                onError = ::doOnTransactionError
            )
        }
    }

    private fun doOnSuccess(unit: Unit) {
        _transactionLiveData.value = State.Content(unit)
    }

    private fun doOnLoginError(error: ApiResultWrapper.Error) {
        Log.d("ErrorBody", error.msg)
        _transactionLiveData.value = State.Error(TransactionState.LOADING)
    }

    private fun doOnNetworkError(error: ApiResultWrapper.Error) {
        Log.d("ErrorBody", error.msg)
        _transactionLiveData.value = State.Error(TransactionState.LOADING)
    }

    private fun doOnTransactionError(error: ApiResultWrapper.Error){
        Log.d("ErrorBody", error.msg)
        _transactionLiveData.value = State.Error(TransactionState.NO_MONEY)
    }

}