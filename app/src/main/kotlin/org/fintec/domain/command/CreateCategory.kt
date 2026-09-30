package org.fintec.domain.command

import org.fintec.domain.repositories.CategoryRepositoryType
import org.fintec.domain.repositories.CategoryRepositoryExistsParams
import org.fintec.domain.repositories.CategoryRepositoryCreateParams
import org.fintec.domain.models.Category
import java.util.UUID

class CreateCategory(
    private val repository: CategoryRepositoryType
) {
    data class Parameters(
        val name: String?,
        val userId: String?,
        val hexColor: String?
    )

    fun run(params: Parameters) {
        require(!params.name.isNullOrEmpty()) { "Name must have a non-empty value" }
        require(!params.userId.isNullOrEmpty()) { "User id must have a non-empty value" }
        require(!params.hexColor.isNullOrEmpty()) { "Hex must have a non-empty value" }
        val exsistsParams = CategoryRepositoryExistsParams(params.name, params.userId)
        val exists = repository.exists(exsistsParams)
        check(!exists) { "Category already exists with name: $params.name" }
        val category = Category(UUID.randomUUID().toString(), params.name, params.userId, params.hexColor)
        repository.create(params = CategoryRepositoryCreateParams(category))
    }
}