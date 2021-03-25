package com.kvad.totalizator.transactionfeature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.transactionfeature.model.TransactionModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class WithdrawViewModel @Inject constructor(

) : ViewModel() {

    private var _withdrawLiveData = MutableLiveData<State<Unit, WithdrawErrorState>>()
    val withdrawLiveData : LiveData<State<Unit, WithdrawErrorState>> = _withdrawLiveData

    fun doWithdraw(transactionMode : TransactionModel){
        _withdrawLiveData.value = State.Loading
        viewModelScope.launch{

        }
    }

}
