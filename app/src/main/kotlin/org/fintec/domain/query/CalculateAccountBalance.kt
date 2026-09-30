package org.fintec.domain.query

import org.fintec.domain.repositories.TransactionRepositoryGetParams
import org.fintec.domain.repositories.TransactionRepositoryType

import org.fintec.domain.models.Money
import org.fintec.domain.repositories.AccountRepositoryGetOneParams
import org.fintec.domain.repositories.AccountRepositoryType

class CalculateAccountBalance(
    private val accountRepository: AccountRepositoryType,
    private val transactionRepository: TransactionRepositoryType
) {
    data class Parameters(
        val accountId: String
    )


    fun run(params: Parameters): Money {
        val account = accountRepository.getOneAccount(AccountRepositoryGetOneParams(params.accountId))
        check(account != null) { "No account was found with the id: $params.accountId" }
        val transactions = transactionRepository.getTransactions(TransactionRepositoryGetParams(params.accountId))
        var amount = account.initialBalance.value
        for (transaction in transactions) {
            amount += transaction.amount.value
        }
        return Money(amount, account.initialBalance.currency)
    }
}