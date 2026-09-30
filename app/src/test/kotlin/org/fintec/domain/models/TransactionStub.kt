package org.fintec.domain.models

import kotlin.time.Instant

fun transactionStub(
    id: String = "any id",
    userId: String = "any user",
    accountId: String = "any account",
    categoryId: String = "any category",
    transferGroupId: String? = null,
    type: TransactionType = TransactionType.INCOME,
    amount: Money = moneyStub(),
    bookingDate: Instant = Instant.fromEpochMilliseconds(0),
    description: String? = null,
    deleteDate: Instant? = null
): Transaction {
    return Transaction(
        id = id,
        userId = userId,
        accountId = accountId,
        categoryId = categoryId,
        transferGroupId = transferGroupId,
        type = type,
        amount = amount,
        bookingDate = bookingDate,
        description = description,
        deleteDate = deleteDate
    )
}
