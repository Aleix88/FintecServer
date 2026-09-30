package org.fintec.domain.command

import org.fintec.domain.mocks.repositories.BudgetRepositoryMock
import org.fintec.domain.mocks.repositories.CategoryRepositoryMock
import org.fintec.domain.models.moneyStub
import org.fintec.domain.repositories.BudgetRepositoryCreateParams
import org.fintec.domain.repositories.CategoryRepositoryExistsParams
import java.time.ZoneId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateBudgetTests {
    private lateinit var budgetRepository: BudgetRepositoryMock
    private lateinit var categoryRepository: CategoryRepositoryMock
    private lateinit var sut: CreateBudget

    @BeforeEach
    fun setUp() {
        budgetRepository = BudgetRepositoryMock()
        categoryRepository = CategoryRepositoryMock().also { it.existsResult = true }
        sut = CreateBudget(budgetRepository, categoryRepository, ZoneId.of("UTC"))
    }

    @Test
    fun `given valid parameters creates budget after checking category`() {
        val current = java.time.YearMonth.now(ZoneId.of("UTC"))
        sut.run(CreateBudget.Parameters("user", "category", moneyStub(250.0), current.monthValue, current.year))

        assertEquals(listOf(CategoryRepositoryExistsParams("category", "user")), categoryRepository.existsCalls)
        val created = budgetRepository.receivedCreateParams.single().budget
        assertEquals("category", created.categoryId)
        assertEquals(moneyStub(250.0), created.amountLimit)
        assertEquals(current.monthValue, created.month)
        assertEquals(current.year, created.year)
        assertEquals(false, created.alert80Sent)
        assertEquals(false, created.alert100Sent)
    }

    @Test
    fun `given missing category does not create budget`() {
        categoryRepository.existsResult = false

        assertThrows(IllegalArgumentException::class.java) {
            sut.run(validParams())
        }
        assertEquals(0, budgetRepository.receivedCreateParams.size)
    }

    @Test
    fun `given past month throws without creating budget`() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.run(validParams(month = 1, year = 2020))
        }
        assertEquals(0, budgetRepository.receivedCreateParams.size)
    }

    @Test
    fun `given invalid month throws`() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.run(validParams(month = 13, year = 2026))
        }
    }

    private fun validParams(month: Int = 1, year: Int = 2020) =
        CreateBudget.Parameters("user", "category", moneyStub(100.0), month, year)
}
