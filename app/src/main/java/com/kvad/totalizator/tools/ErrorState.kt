package com.kvad.totalizator.tools

sealed class ErrorState {
    object LoginError : ErrorState()
    object LoadingError : ErrorState()
}
