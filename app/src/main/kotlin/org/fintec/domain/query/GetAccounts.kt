package org.fintec.domain.query

import org.fintec.domain.models.Account
import org.fintec.domain.repositories.AccountRepositoryGetParams
import org.fintec.domain.repositories.AccountRepositoryType

class GetAccounts(
    private val repository: AccountRepositoryType
) {
    data class Parameters(
        val userId: String
    )

    fun run(params: Parameters): List<Account> {
        return repository.getAccounts(AccountRepositoryGetParams(userId = params.userId))
    }
}
