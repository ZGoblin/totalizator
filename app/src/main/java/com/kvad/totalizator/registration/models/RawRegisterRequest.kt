package com.kvad.totalizator.registration.models

data class RawRegisterRequest(
    val email: String,
    val password: String,
    val day: Int,
    val month: Int,
    val year: Int
)
