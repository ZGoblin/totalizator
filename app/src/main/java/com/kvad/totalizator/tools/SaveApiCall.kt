package com.kvad.totalizator.tools

import android.util.Log
import com.kvad.totalizator.shared.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

@Suppress("TooGenericExceptionCaught")
suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(Dispatchers.IO) {
        try {
            ResultWrapper.Success(apiCall())
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    Log.d("safeApiCall", throwable.code().toString())
                    when (throwable.code()) {
                        LOGGING_ERROR_CODE -> ResultWrapper.LoginError
                        else -> ResultWrapper.DataLoadingError
                    }
                }
                else -> {
                    Log.d("safeApiCall", throwable.message.toString())
                    ResultWrapper.DataLoadingError
                }
            }
        }
    }
}

//TODO если бєк будет присылать кастомные ошибки
//    private fun convertErrorBody(exception: HttpException): ErrorResponse? {
//        return try {
//            exception.response()?.errorBody()?.source()?.let {
//                val token = object : TypeToken<ErrorResponse>() {}.type
//                gson.fromJson(it.toString(), token)
//            }
//        } catch (exception: Exception) {
//            null
//        }
//    }
