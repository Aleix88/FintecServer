package org.fintec.domain.query

import org.fintec.domain.repositories.CategoryRepositoryGetAllParams
import org.fintec.domain.repositories.CategoryRepositoryType
import org.fintec.domain.models.Category

class GetAllCategories(
    private val repository: CategoryRepositoryType
) {
    data class Parameters(
        val userId: String?
    )

    fun run(params: Parameters): List<Category> {
        require(!params.userId.isNullOrEmpty()) { "User id must have a non-empty value" }
        return repository.getAll(CategoryRepositoryGetAllParams(params.userId))
    }
}