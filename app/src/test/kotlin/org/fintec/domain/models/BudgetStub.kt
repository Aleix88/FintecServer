package org.fintec.domain.models

fun budgetStub(
    id: String = "any id",
    categoryId: String = "any category",
    amountLimit: Money = moneyStub(100.0),
    month: Int = 1,
    year: Int = 2026,
    alert80Sent: Boolean = false,
    alert100Sent: Boolean = false
): Budget {
    return Budget(
        id = id,
        categoryId = categoryId,
        amountLimit = amountLimit,
        month = month,
        year = year,
        alert80Sent = alert80Sent,
        alert100Sent = alert100Sent
    )
}
