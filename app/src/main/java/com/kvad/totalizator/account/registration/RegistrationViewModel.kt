package com.kvad.totalizator.account.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.account.registration.RegisterState
import com.kvad.totalizator.account.registration.domain.RegisterUseCase
import com.kvad.totalizator.account.registration.models.RawRegisterRequest
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel(){

    private val _registerStateLiveData = MutableLiveData<RegisterState>()
    val registerStateLiveData: LiveData<RegisterState> = _registerStateLiveData

    fun register(registerRequest: RawRegisterRequest) {
        viewModelScope.launch {
            Log.d("ErrorTAGTAG", registerRequest.toString())
            _registerStateLiveData.value = registerUseCase.register(registerRequest)
        }
    }
}
