package org.fintec.data.repositories

import org.fintec.data.db.CacheDB
import org.fintec.domain.models.Category
import org.fintec.domain.repositories.CategoryRepositoryCreateParams
import org.fintec.domain.repositories.CategoryRepositoryExistsParams
import org.fintec.domain.repositories.CategoryRepositoryGetAllParams
import org.fintec.domain.repositories.CategoryRepositoryType

class CategoriesRepository: CategoryRepositoryType {

    override fun create(params: CategoryRepositoryCreateParams) {
        CacheDB.categories.add(params.category)
    }

    override fun exists(params: CategoryRepositoryExistsParams): Boolean {
        return CacheDB.categories.filter { 
            it.userId == params.userId && it.name == params.categoryId
        }.isNotEmpty()
    }

    override fun getAll(params: CategoryRepositoryGetAllParams): List<Category> {
        return CacheDB.categories.filter { it.userId == params.userId }
    }
}