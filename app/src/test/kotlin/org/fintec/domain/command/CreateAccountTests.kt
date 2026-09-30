package org.fintec.domain.command

import org.fintec.domain.mocks.repositories.AccountRepositoryMock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateAccountTests {
    private lateinit var repository: AccountRepositoryMock
    private lateinit var sut: CreateAccount

    @BeforeEach
    fun setUp() {
        repository = AccountRepositoryMock()
        sut = CreateAccount(repository)
    }

    @Test
    fun `given valid parameters creates account in repository`() {
        sut.run(CreateAccount.Parameters("user", "Main", "EUR", 25.0))

        val created = repository.createAccountCalls.single().account
        assertEquals("user", created.userId)
        assertEquals("Main", created.name)
        assertEquals(25.0, created.initialBalance.value)
        assertEquals("EUR", created.initialBalance.currency)
    }

    @Test
    fun `given invalid currency throws without creating account`() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.run(CreateAccount.Parameters("user", "Main", "USD", 25.0))
        }
        assertEquals(0, repository.createAccountCalls.size)
    }

    @Test
    fun `given negative initial balance throws without creating account`() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.run(CreateAccount.Parameters("user", "Main", "EUR", -1.0))
        }
        assertEquals(0, repository.createAccountCalls.size)
    }
}
