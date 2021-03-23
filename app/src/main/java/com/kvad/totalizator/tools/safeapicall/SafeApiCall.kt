package com.kvad.totalizator.tools.safeapicall

import android.util.Log
import com.kvad.totalizator.tools.LOGGING_ERROR_CODE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(call: suspend () -> Response<T>) = withContext(Dispatchers.IO) {
    try {
        val response = call.invoke()
        val code = response.code()
        Log.d("TAG","code -- $code")
        if (response.isSuccessful) {
            ApiResultWrapper.Success(response.body()!!)
        } else {
            Log.d("TAG","code -- $code")
            when (code) {
                LOGGING_ERROR_CODE -> ApiResultWrapper.Error.LoginError(
                    response.errorBody()?.string() ?: "empty error body"
                )
                else -> ApiResultWrapper.Error.NetworkError(response.errorBody()?.string() ?: "empty error body")
            }
        }
    } catch (throwable: IOException) {
        throwable.printStackTrace()
        ApiResultWrapper.Error.UnknownError(throwable.message.orEmpty())
    }
}

inline fun <SOURCE, RESULT> ApiResultWrapper<SOURCE>.map(mapper: (SOURCE) -> RESULT): ApiResultWrapper<RESULT> {
    return when (this) {
        is ApiResultWrapper.Success -> ApiResultWrapper.Success(mapper(value))
        is ApiResultWrapper.Error -> this
    }
}
