package org.fintec.domain.command

import org.fintec.domain.mocks.repositories.AccountRepositoryMock
import org.fintec.domain.models.accountStub
import org.fintec.domain.repositories.AccountRepositoryGetOneParams
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.time.Instant

class SoftDeleteAccountTests {
    private lateinit var repository: AccountRepositoryMock
    private lateinit var sut: SoftDeleteAccount

    @BeforeEach
    fun setUp() {
        repository = AccountRepositoryMock()
        sut = SoftDeleteAccount(repository)
    }

    @Test
    fun `given active account marks it as deleted`() {
        repository.oneAccountResult = accountStub(id = "account")

        sut.run(SoftDeleteAccount.Parameters("account"))

        assertEquals(listOf(AccountRepositoryGetOneParams("account")), repository.getOneAccountCalls)
        val updated = repository.updateAccountCalls.single().account
        assertEquals("account", updated.id)
        assertNotNull(updated.deleteDate)
    }

    @Test
    fun `given unknown account throws without updating`() {
        assertThrows(IllegalStateException::class.java) {
            sut.run(SoftDeleteAccount.Parameters("unknown"))
        }
        assertEquals(0, repository.updateAccountCalls.size)
    }

    @Test
    fun `given already deleted account throws without updating`() {
        repository.oneAccountResult = accountStub(deleteDate = Instant.fromEpochMilliseconds(1))

        assertThrows(IllegalStateException::class.java) {
            sut.run(SoftDeleteAccount.Parameters("any id"))
        }
        assertEquals(0, repository.updateAccountCalls.size)
    }

    @Test
    fun `given empty account id throws`() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.run(SoftDeleteAccount.Parameters(""))
        }
        assertEquals(0, repository.getOneAccountCalls.size)
    }
}
