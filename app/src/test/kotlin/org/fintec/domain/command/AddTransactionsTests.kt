package org.fintec.domain.command

import org.fintec.domain.mocks.repositories.AccountRepositoryMock
import org.fintec.domain.mocks.repositories.TransactionRepositoryMock
import org.fintec.domain.models.TransactionType
import org.fintec.domain.repositories.AccountRepositoryCheckIfExistsParams
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddTransactionsTests {
    private lateinit var transactionRepository: TransactionRepositoryMock
    private lateinit var accountRepository: AccountRepositoryMock
    private lateinit var sut: AddTransactions

    @BeforeEach
    fun setUp() {
        transactionRepository = TransactionRepositoryMock()
        accountRepository = AccountRepositoryMock().also { it.checkIfExistsResult = true }
        sut = AddTransactions(transactionRepository, accountRepository)
    }

    @Test
    fun `given expense creates one negative transaction`() {
        sut.run(params(type = "expense", amount = 12.5))

        val transaction = transactionRepository.createTransactionCalls.single().transaction
        assertEquals(TransactionType.EXPENSE, transaction.type)
        assertEquals(-12.5, transaction.amount.value)
        assertEquals("origin", transaction.accountId)
        assertEquals("user", transaction.userId)
        assertNull(transaction.transferGroupId)
        assertEquals(listOf(AccountRepositoryCheckIfExistsParams("origin")), accountRepository.checkIfExistsCalls)
    }

    @Test
    fun `given income creates one positive transaction`() {
        sut.run(params(type = "income", amount = -9.0))

        val transaction = transactionRepository.createTransactionCalls.single().transaction
        assertEquals(TransactionType.INCOME, transaction.type)
        assertEquals(9.0, transaction.amount.value)
    }

    @Test
    fun `given transfer creates linked debit and credit transactions`() {
        sut.run(params(type = "transfer", destinationAccountId = "destination", amount = 20.0))

        val transactions = transactionRepository.createTransactionCalls.map { it.transaction }
        assertEquals(2, transactions.size)
        assertEquals(-20.0, transactions[0].amount.value)
        assertEquals(20.0, transactions[1].amount.value)
        assertEquals("origin", transactions[0].accountId)
        assertEquals("destination", transactions[1].accountId)
        assertNotNull(transactions[0].transferGroupId)
        assertEquals(transactions[0].transferGroupId, transactions[1].transferGroupId)
    }

    @Test
    fun `given unknown transaction type throws without creating`() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.run(params(type = "unknown"))
        }
        assertEquals(0, transactionRepository.createTransactionCalls.size)
    }

    @Test
    fun `given origin account does not exist throws without creating`() {
        accountRepository.checkIfExistsResult = false

        assertThrows(IllegalArgumentException::class.java) {
            sut.run(params())
        }
        assertEquals(0, transactionRepository.createTransactionCalls.size)
    }

    private fun params(
        type: String = "expense",
        amount: Double = 10.0,
        destinationAccountId: String? = null
    ) = AddTransactions.Parameters(
        userId = "user",
        originAccountId = "origin",
        destinationAccountId = destinationAccountId,
        categoryId = "category",
        type = type,
        amount = amount,
        description = "test"
    )
}
