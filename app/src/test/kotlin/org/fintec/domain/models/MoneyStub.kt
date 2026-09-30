package org.fintec.domain.models

fun moneyStub(value: Double = 0.0, currency: String = "EUR"): Money {
    return Money(value, currency)
}