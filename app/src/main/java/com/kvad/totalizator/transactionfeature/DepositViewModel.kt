package com.kvad.totalizator.transactionfeature

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.transactionfeature.model.TransactionModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class DepositViewModel @Inject constructor(

) : ViewModel(){

    private var _depositLiveData = MutableLiveData<State<Unit,DepositErrorState>>()
    val depositLiveData : LiveData<State<Unit, DepositErrorState>> = _depositLiveData

    fun doTransaction(transactionModel: TransactionModel) {
        _depositLiveData.value = State.Loading
        viewModelScope.launch {
        }
    }

}