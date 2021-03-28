package com.kvad.totalizator.transactionfeature.domain

import com.kvad.totalizator.data.UserRepository
import com.kvad.totalizator.transactionfeature.data.MapperTransactionToTransactionRequest
import com.kvad.totalizator.transactionfeature.withdraw.WithdrawRepository
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class WithdrawUseCaseTest {

    @Test
    fun withdraw() {
        val mockMapper = mockk<MapperTransactionToTransactionRequest>(){

        }

    }
}