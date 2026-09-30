package org.fintec.domain.models

import kotlin.time.Instant

fun accountStub(
    id: String = "any id",
    userId: String = "any user",
    name: String = "any name",
    initialBalance: Money = moneyStub(),
    deleteDate: Instant? = null
): Account {
    return Account(id, userId, name, initialBalance, deleteDate)
}