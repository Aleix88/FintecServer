package org.fintec.data.repositories

import org.fintec.data.db.CacheDB
import org.fintec.domain.models.Budget
import org.fintec.domain.repositories.BudgetRepositoryCreateParams
import org.fintec.domain.repositories.BudgetRepositoryGetParams
import org.fintec.domain.repositories.BudgetRepositoryType

class BudgetRepository: BudgetRepositoryType {
    override fun get(params: BudgetRepositoryGetParams): Budget {
        val budgets = CacheDB.budgets.filter { it.categoryId == params.categoryId && it.month == params.month && it.year == params.year }
        check(budgets.count() == 1) { "Error. Only one budget should exists with the same month and year" }
        return budgets.first()
    }

    override fun create(params: BudgetRepositoryCreateParams) {
        CacheDB.budgets.add(params.budget)
    }
}