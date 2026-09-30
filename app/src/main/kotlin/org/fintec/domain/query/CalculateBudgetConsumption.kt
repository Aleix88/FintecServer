package org.fintec.domain.query

import org.fintec.domain.repositories.TransactionRepositoryGetTotalAmountParams
import org.fintec.domain.repositories.TransactionRepositoryType

import org.fintec.domain.repositories.BudgetRepositoryGetParams
import org.fintec.domain.repositories.BudgetRepositoryType

class CalculateBudgetConsumption(
    private val transactionRepository: TransactionRepositoryType,
    private val budgetRepository: BudgetRepositoryType
) {
    data class Parameters(
        val categoryId: String?,
        val month: Int?,
        val year: Int?
    )

    fun run(params: Parameters): Double {
        require(params.categoryId.isNullOrEmpty().not()) { "categoryId is null or empty" }
        require(params.month != null) { "month is null or empty" }
        require(params.year != null) { "year is null" }
        val budget = budgetRepository.get(BudgetRepositoryGetParams(params.categoryId, params.month, params.year))
        val amount = transactionRepository.getTotalAmount(TransactionRepositoryGetTotalAmountParams(params.categoryId))
        check(amount.currency == budget.amountLimit.currency) { "Transactions currency doesn't match budget currency" }
        return amount.value / budget.amountLimit.value
    }
}