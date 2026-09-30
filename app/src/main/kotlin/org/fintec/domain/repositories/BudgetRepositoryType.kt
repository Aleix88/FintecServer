package org.fintec.domain.repositories

import org.fintec.domain.models.Budget

data class BudgetRepositoryCreateParams(val budget: Budget)
data class BudgetRepositoryGetParams(val categoryId: String, val month: Int, val year: Int)

interface BudgetRepositoryType {
    fun get(params: BudgetRepositoryGetParams): Budget
    fun create(params: BudgetRepositoryCreateParams)
}
