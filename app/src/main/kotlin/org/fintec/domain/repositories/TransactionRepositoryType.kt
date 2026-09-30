package org.fintec.domain.repositories

import org.fintec.domain.models.Money
import org.fintec.domain.models.Transaction

data class TransactionRepositoryCreateParams(val transaction: Transaction)
data class TransactionRepositoryGetParams(val accountId: String)
data class TransactionRepositoryGetTotalAmountParams(val categoryId: String)

interface TransactionRepositoryType {
    fun createTransaction(params: TransactionRepositoryCreateParams)
    fun getTransactions(params: TransactionRepositoryGetParams): List<Transaction>
    fun getTotalAmount(params: TransactionRepositoryGetTotalAmountParams): Money
}
