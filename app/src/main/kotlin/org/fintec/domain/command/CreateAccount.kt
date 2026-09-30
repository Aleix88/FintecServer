package org.fintec.domain.command

import kotlin.uuid.Uuid
import org.fintec.domain.repositories.AccountRepositoryType
import org.fintec.domain.models.Account
import org.fintec.domain.models.Money
import org.fintec.domain.repositories.AccountRepositoryCreateParams

class CreateAccount(
    private val repository: AccountRepositoryType
) {
    data class Parameters(
        val userId: String?,
        val name: String?,
        val currency: String?,
        val initialBalance: Double?
    )

    fun run(params: Parameters) {
        require(!params.name.isNullOrEmpty()) { "Name must have a non-empty value." }
        require(!params.userId.isNullOrEmpty()) { "UserId must have a non-empty value." }
        require(params.currency == "EUR") { "Invalid currency." }
        require(params.initialBalance != null && params.initialBalance >= 0.0) { "Initial balance must be zero or greater." }

        val account = Account(
            id = Uuid.random().toString(),
            userId = params.userId,
            name = params.name,
            initialBalance = Money(params.initialBalance, params.currency)
        )
        val createAccountParams = AccountRepositoryCreateParams(account)
        repository.createAccount(createAccountParams)
    }
}