package com.kvad.totalizator.tools.safeapicall


sealed class ApiResultWrapper<out T> {

    data class Success<out T>(val value: T) : ApiResultWrapper<T>()

    sealed class Error(val msg: String) : ApiResultWrapper<Nothing>() {
        data class UnknownError(val message: String) : Error(message)
        data class NetworkError(val message: String) : Error(message)
        data class LoginError(val message: String) : Error(message)
        data class NoMoneyError(val message : String) : Error(message)
    }

    @Suppress("LongParameterList")
    fun doOnResult(
        onSuccess: ((T) -> Unit)? = null,
        onNetworkError: ((error: Error.NetworkError) -> Unit)? = null,
        onLoginError: ((error: Error.LoginError) -> Unit)? = null,
        onUnknownError: ((error: Error.UnknownError) -> Unit)? = null,
        onNoMoneyError: ((error: Error.NoMoneyError) -> Unit)? = null,
        onError: ((error: Error) -> Unit)? = null,
        onFinish: (() -> Unit)? = null
    ): ApiResultWrapper<T> {
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
            this is Error.UnknownError && onUnknownError != null -> {
                onUnknownError.invoke(this)
            }
            this is Error.NoMoneyError && onNoMoneyError != null -> {
                onNoMoneyError.invoke(this)
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
