package org.fintec.domain.models

import kotlin.time.Instant

data class Budget(
    val id: String,
    val categoryId: String,
    val amountLimit: Money,
    val month: Int,
    val year: Int,
    val alert80Sent: Boolean,
    val alert100Sent: Boolean
)