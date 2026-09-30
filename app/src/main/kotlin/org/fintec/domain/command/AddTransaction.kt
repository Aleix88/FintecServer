package org.fintec.domain.command

import org.fintec.domain.repositories.TransactionRepositoryCreateParams
import org.fintec.domain.repositories.TransactionRepositoryType

import kotlin.math.abs
import kotlin.time.Clock
import kotlin.uuid.Uuid
import org.fintec.domain.repositories.AccountRepositoryType
import org.fintec.domain.models.Money
import org.fintec.domain.models.Transaction
import org.fintec.domain.models.TransactionType
import org.fintec.domain.repositories.AccountRepositoryCheckIfExistsParams

class AddTransactions(
    private val transactionsRepository: TransactionRepositoryType,
    private val accountsRepository: AccountRepositoryType
) {
    data class Parameters(
        val userId: String?,
        val originAccountId: String?,
        val destinationAccountId: String?,
        val categoryId: String?,
        val type: String?,
        val amount: Double?,
        val description: String?
    )

    fun run(params: Parameters) {
        require(!params.userId.isNullOrEmpty()) { "UserId must have a non-empty value" }
        require(!params.originAccountId.isNullOrEmpty()) { "OriginAccountId must have a non-empty value" }
        require(checkAccountExists(params.originAccountId)) { "Origin account with id $params.originAccountId doesn't exists" }
        require(!params.categoryId.isNullOrEmpty()) { "CategoryId must have a non-empty value" }
        require(params.amount != null) { "Amount must have a defined value" }
        val type = TransactionType.fromRawValue(params.type ?: "")
        require(type != null) { "Type must have a valid value" }

        if (type == TransactionType.TRANSFER) {
            require(!params.destinationAccountId.isNullOrEmpty()) { "DestinationAccountId must have a non-empty value for TRANSFER types" }
            require(checkAccountExists(params.destinationAccountId)) { "Destination account with id $params.destinationAccountId doesn't exists" }
            require(params.originAccountId != params.destinationAccountId) { "Origin and destination account ids must be different" }
            val transferGroupId = Uuid.random().toString()
            val originTransaction = createTransaction(params, type, params.originAccountId, transferGroupId, true)
            val destinationTransaction = createTransaction(params, type, params.destinationAccountId, transferGroupId, false)
            transactionsRepository.createTransaction(TransactionRepositoryCreateParams(originTransaction))
            transactionsRepository.createTransaction(TransactionRepositoryCreateParams(destinationTransaction))
        } else {
            val transaction = createTransaction(params, type, params.originAccountId, null, true)
            transactionsRepository.createTransaction(TransactionRepositoryCreateParams(transaction))
        }
    }

    private fun checkAccountExists(accountId: String): Boolean {
        return accountsRepository.checkIfExists(AccountRepositoryCheckIfExistsParams(accountId))
    }

    private fun createTransaction(
        params: Parameters, 
        type: TransactionType, 
        accountId: String, 
        transferGroupId: String?,
        isOriginAccount: Boolean
    ): Transaction {
        val positiveAmount = abs(params.amount ?: 0.0)
        val amount = when(type) {
            TransactionType.INCOME -> positiveAmount
            TransactionType.EXPENSE -> -positiveAmount
            TransactionType.TRANSFER -> if(isOriginAccount) -positiveAmount else positiveAmount
        }
        return Transaction(
            id = Uuid.random().toString(),
            userId = params.userId ?: "",
            accountId = accountId,
            categoryId = params.categoryId ?: "",
            transferGroupId = transferGroupId,
            type = type,
            amount = Money(amount),
            bookingDate = Clock.System.now(),
            description = params.description,
            deleteDate = null
        )
    }
}