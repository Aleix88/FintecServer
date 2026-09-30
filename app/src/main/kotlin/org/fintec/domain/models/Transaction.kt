package org.fintec.domain.models

import kotlin.time.Instant

// The amount must be positive for an income, negative for an expense, and positive/negative on a transfer
// depending if its inside the origin or the destination account.
data class Transaction(
    val id: String,
    val userId: String,
    val accountId: String,
    val categoryId: String,
    val transferGroupId: String?, // Id to identify both in and out movements of a transfer, is null for non-transfers types
    val type: TransactionType,
    val amount: Money, // TODO: Money should be represented using base 10
    val bookingDate: Instant,
    val description: String?,
    val deleteDate: Instant?
)

enum class TransactionType(val rawValue: String) {
    INCOME("income"),
    EXPENSE("expense"),
    TRANSFER("transfer");

    companion object {
        fun fromRawValue(value: String): TransactionType? = entries.find { it.rawValue == value }
    }
}