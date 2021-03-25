package com.kvad.totalizator.transactionfeature

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.transactionfeature.domain.WithdrawUseCase
import com.kvad.totalizator.transactionfeature.model.TransactionModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class WithdrawViewModel @Inject constructor(
    private val withdrawUseCase: WithdrawUseCase
) : ViewModel() {

    private var _withdrawLiveData = MutableLiveData<State<Unit, TransactionErrorState>>()
    val withdrawLiveData : LiveData<State<Unit, TransactionErrorState>> = _withdrawLiveData

    fun doWithdraw(transactionMode : TransactionModel){
        _withdrawLiveData.value = State.Loading
        viewModelScope.launch{
            withdrawUseCase.withdraw(transactionMode).doOnResult(
                onSuccess = ::doOnSuccess,
                onLoginError = ::doOnLoginError,
                onNetworkError = ::doOnNetworkError,
                onError = ::doOnTransactionError
            )
        }
    }

    private fun doOnSuccess(unit: Unit) {
        _withdrawLiveData.value = State.Content(unit)
    }

    private fun doOnLoginError(error: ApiResultWrapper.Error) {
        Log.d("ErrorBody", error.msg)
        _withdrawLiveData.value = State.Error(TransactionErrorState.ZERO_ERROR)
    }

    private fun doOnNetworkError(error: ApiResultWrapper.Error) {
        Log.d("ErrorBody", error.msg)
        _withdrawLiveData.value = State.Error(TransactionErrorState.ZERO_ERROR)
    }

    private fun doOnTransactionError(error: ApiResultWrapper.Error){
        Log.d("ErrorBody", error.msg)
        _withdrawLiveData.value = State.Error(TransactionErrorState.ZERO_ERROR)
    }

}
