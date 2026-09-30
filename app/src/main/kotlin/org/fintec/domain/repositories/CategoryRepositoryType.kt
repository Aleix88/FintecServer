package org.fintec.domain.repositories

import org.fintec.domain.models.Category

data class CategoryRepositoryCreateParams(val category: Category)
data class CategoryRepositoryExistsParams(val categoryId: String, val userId: String)
data class CategoryRepositoryGetAllParams(val userId: String)

interface CategoryRepositoryType {
    fun create(params: CategoryRepositoryCreateParams)
    fun exists(params: CategoryRepositoryExistsParams): Boolean
    fun getAll(params: CategoryRepositoryGetAllParams): List<Category>
}
