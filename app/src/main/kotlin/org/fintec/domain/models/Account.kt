package org.fintec.domain.models

import kotlin.time.Instant

data class Account(
    val id: String,
    val userId: String,
    val name: String,
    val initialBalance: Money,
    val deleteDate: Instant? = null)