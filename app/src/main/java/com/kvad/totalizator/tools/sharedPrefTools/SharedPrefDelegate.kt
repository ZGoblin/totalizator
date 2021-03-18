package com.kvad.totalizator.tools.sharedPrefTools

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPrefDelegate<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val defValue: T,
) : ReadWriteProperty<Any?, T> {
    companion object {
        const val ERROR_MESSAGE = "Unknown type value"
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = with(sharedPreferences.edit()) {
        when (value) {
            is String -> putString(key, value)
            is Boolean -> putBoolean(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> throw IllegalAccessError(ERROR_MESSAGE)
        }.apply()
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = with(sharedPreferences) {
        return when (defValue) {
            is String -> getString(key, defValue)
            is Boolean -> getBoolean(key, defValue)
            is Int -> getInt(key, defValue)
            is Long -> getLong(key, defValue)
            is Float -> getFloat(key, defValue)
            else -> null
        } as T
    }
}
