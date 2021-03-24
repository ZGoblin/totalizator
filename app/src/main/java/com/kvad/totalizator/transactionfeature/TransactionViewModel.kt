package com.kvad.totalizator.transactionfeature

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

typealias transactionStateLiveData = State<Unit, ErrorState>

class TransactionViewModel @Inject constructor(
    private val transactionUseCase : TransactionUseCase
) : ViewModel() {

    private var _transactionLiveData = MutableLiveData<transactionStateLiveData>()
    val transactionLiveData: LiveData<transactionStateLiveData> = _transactionLiveData

    fun doTransaction(transactionModel: TransactionModel) {
        _transactionLiveData.value = State.Loading
        viewModelScope.launch {
            transactionUseCase.deposit(transactionModel).doOnResult (
                onSuccess = ::doOnSuccess,
                onLoginError = ::doOnLoginError,
                onNetworkError = ::doOnNetworkError
            )
        }
    }

    private fun doOnSuccess(unit : Unit){
        _transactionLiveData.value = State.Content(unit)
    }

    private fun doOnLoginError(apiResultWrapper: ApiResultWrapper.Error){
        _transactionLiveData.value = State.Error(ErrorState.LOGIN_ERROR)
    }

    private fun doOnNetworkError(apiResultWrapper: ApiResultWrapper.Error){
        _transactionLiveData.value = State.Error(ErrorState.LOADING_ERROR)
    }

}