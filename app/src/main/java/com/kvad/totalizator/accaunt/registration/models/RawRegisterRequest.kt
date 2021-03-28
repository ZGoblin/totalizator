package com.kvad.totalizator.accaunt.registration.models

data class RawRegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val day: Int,
    val month: Int,
    val year: Int
)
