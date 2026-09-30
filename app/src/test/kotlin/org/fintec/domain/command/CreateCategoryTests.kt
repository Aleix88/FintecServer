package org.fintec.domain.command

import org.fintec.domain.mocks.repositories.CategoryRepositoryMock
import org.fintec.domain.repositories.CategoryRepositoryExistsParams
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateCategoryTests {
    private lateinit var repository: CategoryRepositoryMock
    private lateinit var sut: CreateCategory

    @BeforeEach
    fun setUp() {
        repository = CategoryRepositoryMock()
        sut = CreateCategory(repository)
    }

    @Test
    fun `given valid parameters checks duplicate and creates category`() {
        sut.run(CreateCategory.Parameters("Food", "user", "#ffffff"))

        assertEquals(listOf(CategoryRepositoryExistsParams("Food", "user")), repository.existsCalls)
        val created = repository.createCalls.single().category
        assertEquals("Food", created.name)
        assertEquals("user", created.userId)
        assertEquals("#ffffff", created.hexColor)
        assertEquals(false, created.id.isBlank())
    }

    @Test
    fun `given existing category throws without creating`() {
        repository.existsResult = true

        assertThrows(IllegalStateException::class.java) {
            sut.run(CreateCategory.Parameters("Food", "user", "#ffffff"))
        }
        assertEquals(0, repository.createCalls.size)
    }

    @Test
    fun `given missing required field throws`() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.run(CreateCategory.Parameters("", "user", "#ffffff"))
        }
        assertEquals(0, repository.createCalls.size)
    }
}
