package org.fintec

import org.fintec.data.repositories.AccountRepository
import org.fintec.domain.command.CreateAccount

fun main() {
    val useCase = CreateAccount(AccountRepository())
    try {
        useCase.run(CreateAccount.Parameters(userId = "", name = "", currency = "", initialBalance = 0.0))
    } catch (e: IllegalArgumentException) {
        println(e)
    }
}
