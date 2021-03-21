package com.kvad.totalizator.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.models.RegisterRequest
import com.kvad.totalizator.registration.domain.RegisterUseCase
import com.kvad.totalizator.registration.models.RawRegisterRequest
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel(){

    private val _registerStateLiveData = MutableLiveData<RegisterState>()
    val registerStateLiveData: LiveData<RegisterState> = _registerStateLiveData

    fun register(registerRequest: RawRegisterRequest) {
        viewModelScope.launch {
            _registerStateLiveData.value = registerUseCase.register(registerRequest)
        }
    }
}