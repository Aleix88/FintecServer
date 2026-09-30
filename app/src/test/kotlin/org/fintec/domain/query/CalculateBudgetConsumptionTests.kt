package org.fintec.domain.query

import org.fintec.domain.mocks.repositories.BudgetRepositoryMock
import org.fintec.domain.mocks.repositories.TransactionRepositoryMock
import org.fintec.domain.models.budgetStub
import org.fintec.domain.models.moneyStub
import org.fintec.domain.models.transactionStub
import org.fintec.domain.repositories.BudgetRepositoryGetParams
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CalculateBudgetConsumptionTests {
    private lateinit var sut: CalculateBudgetConsumption
    private lateinit var budgetRepository: BudgetRepositoryMock
    private lateinit var transactionRepository: TransactionRepositoryMock

    @BeforeEach
    fun setup() {
        budgetRepository = BudgetRepositoryMock()
        transactionRepository = TransactionRepositoryMock()
        sut = CalculateBudgetConsumption(transactionRepository, budgetRepository)
    }

    @ParameterizedTest
    @CsvSource(
        "1000.0, 500.0, 0.5",
        "1000.0, 1000.0, 1.0",
        "10.0, 2.0, 0.2"
    )
    fun `given budget and total amount then calculate budget consumption`(
        limit: Double, totalAmount: Double, expectedResult: Double
    ) {
        // Given
        val categoryId = "any id"
        val month = 1
        val year = 2020
        budgetRepository.getResult = Result.success(budgetStub(
            categoryId = categoryId,
            amountLimit = moneyStub(limit),
            month = month,
            year = year))
        transactionRepository.totalAmountResult = moneyStub(totalAmount)
        // When
        val result = sut.run(CalculateBudgetConsumption.Parameters(categoryId, month, year))
        // Then
        val expectedParameters = BudgetRepositoryGetParams(categoryId, month, year)
        assert(budgetRepository.receivedGetParams == listOf(expectedParameters))
        assert(result == expectedResult)
    }

    // ERROR CASES
    @Test
    fun `given budget repository throws then run propagates exception`() {
        val expected = IllegalStateException("Budget repository failed")
        budgetRepository.getResult = Result.failure(expected)

        val actual = assertThrows<IllegalStateException> {
            sut.run(CalculateBudgetConsumption.Parameters("id", 1, 2026))
        }

        assertSame(expected, actual)
    }

    @Test
    fun `given transaction repository throws then run propagates exception`() {
        budgetRepository.getResult = Result.success(budgetStub(categoryId = "id"))
        val expected = IllegalStateException("Transaction repository failed")
        transactionRepository.totalAmountException = expected

        val actual = assertThrows<IllegalStateException> {
            sut.run(CalculateBudgetConsumption.Parameters("id", 1, 2026))
        }

        assertSame(expected, actual)
    }

    @Test
    fun `given nil category id then throw exception`() {
        assertThrows<IllegalArgumentException> {
            sut.run(CalculateBudgetConsumption.Parameters(null, 1, 1))
        }
    }

    @Test
    fun `given nil month then throw exception`() {
        assertThrows<IllegalArgumentException> {
            sut.run(CalculateBudgetConsumption.Parameters("id", null, 1))
        }
    }

    @Test
    fun `given nil year then throw exception`() {
        assertThrows<IllegalArgumentException> {
            sut.run(CalculateBudgetConsumption.Parameters("id", 1, null))
        }
    }
}
