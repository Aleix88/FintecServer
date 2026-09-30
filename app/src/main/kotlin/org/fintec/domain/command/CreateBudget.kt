package org.fintec.domain.command

import org.fintec.domain.repositories.BudgetRepositoryCreateParams
import org.fintec.domain.repositories.BudgetRepositoryType
import org.fintec.domain.repositories.CategoryRepositoryType
import org.fintec.domain.repositories.CategoryRepositoryExistsParams
import org.fintec.domain.models.Budget
import org.fintec.domain.models.Money
import java.time.YearMonth
import java.time.ZoneId

class CreateBudget(
    private val budgetRepository: BudgetRepositoryType,
    private val categoriesRepository: CategoryRepositoryType,
    private val zoneId: ZoneId
) {
    data class Parameters(
        val userId: String?,
        val categoryId: String?,
        val amountLimit: Money?,
        val month: Int?,
        val year: Int?
    )

    fun run(params: Parameters) {
        require(params.userId.isNullOrEmpty().not()) { "user id can't be null" }
        require(params.categoryId.isNullOrEmpty().not()) { "Category ID is required" }
        require(params.amountLimit != null) { "Amount limit is required" }
        require(params.month != null && params.month >= 1 && params.month <= 12) { "Month is required" }
        require(params.year != null && params.year > 2000) { "Year is required" }
        require(isValidDate(params.month, params.year)) { "Month and year should be equal or posterior to the current month and year" }

        val existsParameters = CategoryRepositoryExistsParams(params.categoryId, params.userId)
        require(categoriesRepository.exists(existsParameters)) {
            "Category does not exist with id ${params.categoryId} and userId ${params.userId}"
        }
        val budget = Budget(
            params.userId,
            params.categoryId,
            params.amountLimit,
            params.month,
            params.year,
            alert80Sent = false,
            alert100Sent = false
        )
        budgetRepository.create(BudgetRepositoryCreateParams(budget))
    }

    private fun isValidDate(month: Int, year: Int): Boolean {
        val current = YearMonth.now(zoneId)
        val budgetMonth = YearMonth.of(year, month)
        return !budgetMonth.isBefore(current)
    }
}