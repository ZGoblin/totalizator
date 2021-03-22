package com.kvad.totalizator.tools

import com.kvad.totalizator.tools.ResultWrapper.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

sealed class ResultWrapper<out T> {

    data class Success<out T>(val value: T) : ResultWrapper<T>()

    sealed class Error(val msg: String) : ResultWrapper<Nothing>() {
        data class UnknownError(val message: String) : Error(message)
        data class NetworkError(val message: String) : Error(message)
        data class LoginError(val message: String) : Error(message)
    }

    fun doOnResult(
        onSuccess: ((T) -> Unit)? = null,
        onNetworkError: ((error: Error.NetworkError) -> Unit)? = null,
        onLoginError: ((error: Error.LoginError) -> Unit)? = null,
        onUnknownError: ((error: UnknownError) -> Unit)? = null,
        onError: ((error: Error) -> Unit)? = null,
        onFinish: (() -> Unit)? = null
    ): ResultWrapper<T> {
        when {
            this is Success -> {
                onSuccess?.invoke(value)
            }
            this is Error.NetworkError && onNetworkError != null -> {
                onNetworkError.invoke(this)
            }
            this is Error.LoginError && onLoginError != null -> {
                onLoginError.invoke(this)
            }
            this is UnknownError && onUnknownError != null -> {
                onUnknownError.invoke(this)
            }
            this is Error -> {
                onError?.invoke(this)
            }
            else -> {
                throw IllegalStateException("Unknown state in ResultWrapper: $this")
            }
        }
        onFinish?.invoke()
        return this
    }

    fun isSuccess(): Boolean = this is Success
    fun asSuccess(): Success<T> = this as Success<T>
    fun isError(): Boolean = this is Error
    fun asError(): Error = this as Error
}


suspend fun <T> safeApiCall(call: suspend () -> Response<T>) = withContext(Dispatchers.IO) {
        try {
            val response = call.invoke()
            val code = response.code()
            if (response.isSuccessful) {
                ResultWrapper.Success(response.body()!!)
            } else {
                when (code) {
                    LOGGING_ERROR_CODE -> Error.LoginError(
                        response.errorBody()?.string() ?: "empty error body"
                    )
                    else -> Error.NetworkError(response.errorBody()?.string() ?: "empty error body")
                }
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Error.UnknownError(throwable.message.orEmpty())
        }
    }

inline fun <SOURCE, RESULT> ResultWrapper<SOURCE>.map(mapper: (SOURCE) -> RESULT): ResultWrapper<RESULT> {
    return when (this) {
        is ResultWrapper.Success -> ResultWrapper.Success(mapper(value))
        is Error -> this
    }
}