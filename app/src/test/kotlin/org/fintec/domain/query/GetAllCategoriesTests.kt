package org.fintec.domain.query

import org.fintec.domain.mocks.repositories.CategoryRepositoryMock
import org.fintec.domain.models.Category
import org.fintec.domain.repositories.CategoryRepositoryGetAllParams
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetAllCategoriesTests {
    private lateinit var sut: GetAllCategories
    private lateinit var repository: CategoryRepositoryMock

    @BeforeEach
    fun setUp() {
        repository = CategoryRepositoryMock()
        sut = GetAllCategories(repository)
    }

    @Test
    fun `given user id then returns categories and passes user id`() {
        val category = Category("id", "Food", "user-1", "#ffffff")
        repository.categoriesResult.add(category)

        val result = sut.run(GetAllCategories.Parameters("user-1"))

        assertEquals(listOf(category), result)
        assertEquals(listOf(CategoryRepositoryGetAllParams("user-1")), repository.getAllCalls)
    }

    @Test
    fun `given null user id then throws`() {
        assertThrows<IllegalArgumentException> {
            sut.run(GetAllCategories.Parameters(null))
        }
    }

    @Test
    fun `given empty user id then throws`() {
        assertThrows<IllegalArgumentException> {
            sut.run(GetAllCategories.Parameters(""))
        }
    }
}
