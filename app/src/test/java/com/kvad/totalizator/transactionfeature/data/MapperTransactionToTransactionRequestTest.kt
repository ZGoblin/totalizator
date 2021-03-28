package com.kvad.totalizator.transactionfeature.data

import com.kvad.totalizator.data.requestmodels.TransactionRequest
import com.kvad.totalizator.transactionfeature.domain.TransactionType
import com.kvad.totalizator.transactionfeature.model.TransactionModel
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class MapperTransactionToTransactionRequestTest {

    @TestFactory
    fun `map transaction model to transaction request`(): List<DynamicTest> {
        val amount = 100.0
        val mapper = MapperTransactionToTransactionRequest()
        return listOf(
            TransactionType.WITHDRAW to "withdraw",
            TransactionType.DEPOSIT to "deposit"
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("When input $input, transaction request should be $expected ") {
                mapper.map(
                    TransactionModel(
                        amount = amount,
                        type = input
                    )
                ) shouldBe TransactionRequest(amount = amount, type = expected)
            }

        }

    }
}