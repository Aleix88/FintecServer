package org.fintec.domain.query

import org.fintec.domain.mocks.repositories.AccountRepositoryMock
import org.fintec.domain.models.accountStub
import org.fintec.domain.repositories.AccountRepositoryGetParams
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetAccountsTests {
    private lateinit var sut: GetAccounts
    private lateinit var repository: AccountRepositoryMock

    @BeforeEach
    fun setUp() {
        repository = AccountRepositoryMock()
        sut = GetAccounts(repository)
    }

    @Test
    fun `given user id then returns repository accounts and passes user id`() {
        val account = accountStub(userId = "user-1")
        repository.accountsResult.add(account)

        val result = sut.run(GetAccounts.Parameters("user-1"))

        assertEquals(listOf(account), result)
        assertEquals(listOf(AccountRepositoryGetParams("user-1")), repository.getAccountsCalls)
    }
}
