package org.fintec.domain.models

data class Money(
    val value: Double,
    val currency: String = "EUR"
)