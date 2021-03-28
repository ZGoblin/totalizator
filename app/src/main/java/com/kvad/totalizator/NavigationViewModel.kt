package com.kvad.totalizator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kvad.totalizator.shared.SharedPref
import javax.inject.Inject

class NavigationViewModel @Inject constructor(
    sharedPref: SharedPref
): ViewModel() {

    private val _openNavigationLiveData = MutableLiveData<OpenNavigator>()
    val openNavigatorLiveData: LiveData<OpenNavigator> = _openNavigationLiveData

    init {
        if (sharedPref.isFirstOpened) {
            sharedPref.isFirstOpened = false
            _openNavigationLiveData.value = OpenNavigator.FIRST_APP_OPEN
        }
    }

}
