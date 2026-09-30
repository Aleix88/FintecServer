package org.fintec.domain.mocks.repositories

import org.fintec.domain.models.Budget
import org.fintec.domain.repositories.BudgetRepositoryCreateParams
import org.fintec.domain.repositories.BudgetRepositoryGetParams
import org.fintec.domain.repositories.BudgetRepositoryType

class BudgetRepositoryMock: BudgetRepositoryType {
    var getResult: Result<Budget>? = null
    var receivedGetParams = mutableListOf<BudgetRepositoryGetParams>()
    var receivedCreateParams = mutableListOf<BudgetRepositoryCreateParams>()

    override fun get(params: BudgetRepositoryGetParams): Budget {
        val result = getResult?.getOrThrow()
        requireNotNull(result) { "Must provide at least one budget" }
        receivedGetParams.add(params)
        return result
    }

    override fun create(params: BudgetRepositoryCreateParams) {
        receivedCreateParams.add(params)
    }
}