package org.fintec.domain.query

import org.fintec.domain.repositories.TransactionRepositoryGetParams
import org.fintec.domain.repositories.TransactionRepositoryType

import org.fintec.domain.models.Transaction

class GetTransactions(
    private val repository: TransactionRepositoryType
) {
    data class Parameters(val accountId: String?)

    fun run(params: Parameters): List<Transaction> {
        require(!params.accountId.isNullOrEmpty()) { "AccountId must have a non-empty value" }
        return repository.getTransactions(TransactionRepositoryGetParams(params.accountId))
    }
}