package com.kvad.totalizator.tools.sharedPrefTools

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPref @Inject constructor(context: Context) {

    private companion object {
        const val PRIVATE_SHARED_PREF = "PRIVATE_SHARED_PREF"
        const val KEY_TOKEN = "KEY_TOKEN"
        const val IS_FIRST_OPEN_KEY = "IS_FIRST_OPEN_KEY"
        const val DEFAULT_VALUE_TOKEN = "DEFAULT_VALUE_TOKEN"
        const val DEFAULT_VALUE_IS_FIRST_OPEN = true
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PRIVATE_SHARED_PREF, Context.MODE_PRIVATE)
    }

    var token: String by SharedPrefDelegate(sharedPreferences, KEY_TOKEN, DEFAULT_VALUE_TOKEN)

    var isFirstOpened: Boolean by SharedPrefDelegate(sharedPreferences, IS_FIRST_OPEN_KEY, DEFAULT_VALUE_IS_FIRST_OPEN)
}
