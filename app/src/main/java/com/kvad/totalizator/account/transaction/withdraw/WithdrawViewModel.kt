package com.kvad.totalizator.account.transaction.withdraw

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.account.transaction.TransactionErrorState
import com.kvad.totalizator.account.transaction.domain.WithdrawUseCase
import com.kvad.totalizator.account.transaction.model.TransactionModel
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias WithdrawState = State<Unit, TransactionErrorState>

class WithdrawViewModel @Inject constructor(
    private val withdrawUseCase: WithdrawUseCase
) : ViewModel() {

    private var _withdrawLiveData = MutableLiveData<WithdrawState>()
    val withdrawLiveData : LiveData<WithdrawState> = _withdrawLiveData

    fun doWithdraw(transactionMode : TransactionModel){
        _withdrawLiveData.value = State.Loading
        viewModelScope.launch{
            withdrawUseCase.withdraw(transactionMode).doOnResult(
                onSuccess = ::doOnSuccess,
                onNoMoneyError = ::doOnNoMoneyError

            )
        }
    }

    private fun doOnNoMoneyError(error: ApiResultWrapper.Error.NoMoneyError) {
        Log.d("ErrorBody", error.msg)
        _withdrawLiveData.value = State.Error(TransactionErrorState.NO_MONEY)
    }

    private fun doOnSuccess(unit: Unit) {
        _withdrawLiveData.value = State.Content(unit)
    }

}
