package org.fintec.data.repositories

import org.fintec.domain.repositories.TransactionRepositoryCreateParams
import org.fintec.domain.repositories.TransactionRepositoryGetParams
import org.fintec.domain.repositories.TransactionRepositoryGetTotalAmountParams
import org.fintec.domain.repositories.TransactionRepositoryType

import org.fintec.data.db.CacheDB
import org.fintec.domain.models.Money
import org.fintec.domain.models.Transaction

class TransactionRepository: TransactionRepositoryType {

    override fun createTransaction(params: TransactionRepositoryCreateParams) {
        CacheDB.transactions.add(params.transaction)
    }

    override fun getTransactions(params: TransactionRepositoryGetParams): List<Transaction> {
        return CacheDB.transactions.filter { it.accountId == params.accountId }
    }

    override fun getTotalAmount(params: TransactionRepositoryGetTotalAmountParams): Money {
        val transactions = CacheDB.transactions
            .filter { it.categoryId == params.categoryId }
        val total = transactions
            .map { it.amount.value }
            .reduceOrNull { acc, value -> acc + value } ?: 0.0
        return Money(total)
    }
}
