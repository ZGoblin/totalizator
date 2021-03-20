package com.kvad.totalizator.tools

import com.kvad.totalizator.shared.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SaveApiCall @Inject constructor() {

    @Suppress("TooGenericExceptionCaught")
    suspend fun <T> saveCall(apiCall: suspend () -> T): ResultWrapper<T> {
        return withContext(Dispatchers.IO) {
            try {
                ResultWrapper.Success(apiCall())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        ResultWrapper.GenericError(code)
                    }
                    else -> {
                        ResultWrapper.GenericError()
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
}
