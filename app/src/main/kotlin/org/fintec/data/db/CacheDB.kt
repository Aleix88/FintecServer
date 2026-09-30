package org.fintec.data.db

import org.fintec.domain.models.Account
import org.fintec.domain.models.Budget
import org.fintec.domain.models.Category
import org.fintec.domain.models.Transaction

object CacheDB {
    val accounts = mutableListOf<Account>()
    val transactions = mutableListOf<Transaction>()
    val categories = mutableListOf<Category>()
    val budgets = mutableListOf<Budget>()
}