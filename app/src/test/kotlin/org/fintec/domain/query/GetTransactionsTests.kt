package org.fintec.domain.query

import org.fintec.domain.mocks.repositories.TransactionRepositoryMock
import org.fintec.domain.models.transactionStub
import org.fintec.domain.repositories.TransactionRepositoryGetParams
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetTransactionsTests {
    private lateinit var sut: GetTransactions
    private lateinit var repository: TransactionRepositoryMock

    @BeforeEach
    fun setUp() {
        repository = TransactionRepositoryMock()
        sut = GetTransactions(repository)
    }

    @Test
    fun `given account id then returns transactions and passes account id`() {
        val transaction = transactionStub(accountId = "account-1")
        repository.transactionsResult.add(transaction)

        val result = sut.run(GetTransactions.Parameters("account-1"))

        assertEquals(listOf(transaction), result)
        assertEquals(listOf(TransactionRepositoryGetParams("account-1")), repository.getTransactionsCalls)
    }

    @Test
    fun `given null account id then throws`() {
        assertThrows<IllegalArgumentException> {
            sut.run(GetTransactions.Parameters(null))
        }
    }

    @Test
    fun `given empty account id then throws`() {
        assertThrows<IllegalArgumentException> {
            sut.run(GetTransactions.Parameters(""))
        }
    }
}
