package org.fintec.domain.command

import kotlin.time.Clock
import org.fintec.domain.repositories.AccountRepositoryType
import org.fintec.domain.repositories.AccountRepositoryGetOneParams
import org.fintec.domain.repositories.AccountRepositoryUpdateParams

class SoftDeleteAccount(
    private val repository: AccountRepositoryType
) {
    data class Parameters(val accountId: String?)

    fun run(params: Parameters) {
        require(!params.accountId.isNullOrEmpty()) { "AccountId must have a non-empty value" }
        val account = repository.getOneAccount(AccountRepositoryGetOneParams(params.accountId))
        check(account != null) { "Account doesn't exist for accountId: $params.accountId" }
        check(account.deleteDate == null) { "Account already deleted" }
        repository.updateAccount(AccountRepositoryUpdateParams(account.copy(deleteDate = Clock.System.now())))
    }
}
