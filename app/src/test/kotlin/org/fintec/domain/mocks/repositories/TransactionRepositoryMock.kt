package org.fintec.domain.mocks.repositories

import org.fintec.domain.models.Money
import org.fintec.domain.models.Transaction
import org.fintec.domain.repositories.TransactionRepositoryCreateParams
import org.fintec.domain.repositories.TransactionRepositoryGetParams
import org.fintec.domain.repositories.TransactionRepositoryGetTotalAmountParams
import org.fintec.domain.repositories.TransactionRepositoryType

class TransactionRepositoryMock: TransactionRepositoryType {
    val transactionsResult = mutableListOf<Transaction>()
    var totalAmountResult = Money(0.0)
    var totalAmountException: Throwable? = null
    val createTransactionCalls = mutableListOf<TransactionRepositoryCreateParams>()
    val getTransactionsCalls = mutableListOf<TransactionRepositoryGetParams>()
    val getTotalAmountCalls = mutableListOf<TransactionRepositoryGetTotalAmountParams>()

    override fun createTransaction(params: TransactionRepositoryCreateParams) {
        createTransactionCalls.add(params)
    }

    override fun getTransactions(params: TransactionRepositoryGetParams): List<Transaction> {
        getTransactionsCalls.add(params)
        return transactionsResult
    }

    override fun getTotalAmount(params: TransactionRepositoryGetTotalAmountParams): Money {
        getTotalAmountCalls.add(params)
        totalAmountException?.let { throw it }
        return totalAmountResult
    }
}
