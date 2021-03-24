package com.kvad.totalizator.transactionfeature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
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
                onSuccess = {_transactionLiveData.value = State.Content(it)},
                onError = {_transactionLiveData.value = State.Error(ErrorState.LOGIN_ERROR)},
                onLoginError = {_transactionLiveData.value = State.Error(ErrorState.LOADING_ERROR)}
            )
        }
    }
}