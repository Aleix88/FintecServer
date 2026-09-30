package org.fintec.domain.mocks.repositories

import org.fintec.domain.models.Category
import org.fintec.domain.repositories.CategoryRepositoryCreateParams
import org.fintec.domain.repositories.CategoryRepositoryExistsParams
import org.fintec.domain.repositories.CategoryRepositoryGetAllParams
import org.fintec.domain.repositories.CategoryRepositoryType

class CategoryRepositoryMock: CategoryRepositoryType {
    var existsResult = false
    val createCalls = mutableListOf<CategoryRepositoryCreateParams>()
    val existsCalls = mutableListOf<CategoryRepositoryExistsParams>()

    override fun create(params: CategoryRepositoryCreateParams) {
        createCalls.add(params)
    }

    override fun exists(params: CategoryRepositoryExistsParams): Boolean {
        existsCalls.add(params)
        return existsResult
    }

    val categoriesResult = mutableListOf<Category>()
    val getAllCalls = mutableListOf<CategoryRepositoryGetAllParams>()

    override fun getAll(params: CategoryRepositoryGetAllParams): List<Category> {
        getAllCalls.add(params)
        return categoriesResult
    }
}
