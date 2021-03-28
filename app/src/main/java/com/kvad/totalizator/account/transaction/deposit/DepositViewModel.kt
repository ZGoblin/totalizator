package com.kvad.totalizator.account.transaction.deposit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.account.data.UserRepository
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.account.transaction.TransactionErrorState
import com.kvad.totalizator.account.data.MapperTransactionToTransactionRequest
import com.kvad.totalizator.account.transaction.model.TransactionModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class DepositViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val mapperTransactionToTransactionRequest: MapperTransactionToTransactionRequest
) : ViewModel(){

    private var _depositLiveData = MutableLiveData<State<Unit, TransactionErrorState>>()
    val depositLiveData : LiveData<State<Unit, TransactionErrorState>> = _depositLiveData

    fun deDeposit(transactionModel: TransactionModel) {
        _depositLiveData.value = State.Loading
        viewModelScope.launch {
            val request = mapperTransactionToTransactionRequest.map(transactionModel)
            userRepository.doTransaction(request).doOnResult(
                onSuccess = ::doOnSuccess,
                onError = ::doOnError
            )
        }
    }

    private fun doOnSuccess(unit: Unit) {
        _depositLiveData.value = State.Content(unit)
    }

    private fun doOnError(error: ApiResultWrapper.Error) {
        Log.d("ErrorBody", error.msg)
        _depositLiveData.value = State.Error(TransactionErrorState.LOADING_ERROR)
    }
}
