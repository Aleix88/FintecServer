package org.fintec.domain.query

import org.fintec.domain.mocks.repositories.AccountRepositoryMock
import org.fintec.domain.models.Money
import org.fintec.domain.models.accountStub
import org.fintec.domain.models.moneyStub
import org.fintec.domain.mocks.repositories.TransactionRepositoryMock
import org.fintec.domain.models.Transaction
import org.fintec.domain.models.TransactionType
import org.fintec.domain.models.transactionStub
import kotlin.time.Instant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CalculateAccountBalanceTests {
    private lateinit var sut: CalculateAccountBalance
    private lateinit var accountRepository: AccountRepositoryMock

    private lateinit var transactionRepository: TransactionRepositoryMock

    @BeforeEach
    fun setUp() {
        accountRepository = AccountRepositoryMock()
        transactionRepository = TransactionRepositoryMock()
        sut = CalculateAccountBalance(accountRepository, transactionRepository)
    }

    @Test
    fun `given non existing account id when run then throws error`() {
        // Given
        accountRepository.oneAccountResult = null
        // When - Then
        assertThrows<IllegalStateException>({
            sut.run(CalculateAccountBalance.Parameters("Any account"))
        })
    }

    @Test
    fun `given existing account id and zero transactions then return initial balance`() {
        // Given
        accountRepository.oneAccountResult = accountStub(initialBalance = moneyStub(10.0, "EUR"))
        // When
        val amount = sut.run(CalculateAccountBalance.Parameters("Any account"))
        // Then
        assertEquals(Money(10.0, "EUR"), amount)
    }

    @Test
    fun `given existing account id and transactions then return accumulated balance`() {
        // Given
        accountRepository.oneAccountResult = accountStub(initialBalance = moneyStub(10.0, "EUR"))
        transactionRepository.transactionsResult.addAll(listOf(
            transactionStub(type = TransactionType.EXPENSE, amount = moneyStub(-10.0)),
            transactionStub(type = TransactionType.EXPENSE, amount = moneyStub(-25.0)),
            transactionStub(type = TransactionType.INCOME, amount = moneyStub(60.0)),
        ))
        // When
        val amount = sut.run(CalculateAccountBalance.Parameters("account"))
        // Then
        assertEquals(Money(35.0, "EUR"), amount)
    }
}