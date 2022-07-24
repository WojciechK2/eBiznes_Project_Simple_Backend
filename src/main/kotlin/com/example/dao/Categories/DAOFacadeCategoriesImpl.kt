package com.example.dao.Categories

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.Categories
import com.example.models.Category
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*

class DAOFacadeCategoriesImpl : DAOFacadeCategories {

    private fun resultRowToCategory(row: ResultRow) = Category(
        id = row[Categories.id],
        name = row[Categories.name]
    )

    override suspend fun getCategories(): List<Category> = dbQuery {
        Categories.selectAll().map(::resultRowToCategory)
    }

    override suspend fun getCategory(id: Int): Category? = dbQuery{
        Categories
            .select {Categories.id eq id}
            .map(::resultRowToCategory)
            .singleOrNull()
    }

    override suspend fun addCategory(name: String): Category? = dbQuery {
        val insertStatement = Categories.insert {
            it[Categories.name] = name
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCategory)
    }

    override suspend fun editCategory(id: Int, name: String): Boolean = dbQuery {
        Categories.update({ Categories.id eq id }){
            it[Categories.name] = name
        } > 0
    }

    override suspend fun deleteCategory(id: Int): Boolean = dbQuery {
        Categories.deleteWhere {Categories.id eq id} > 0
    }
}

val daoCategories: DAOFacadeCategories = DAOFacadeCategoriesImpl().apply {
    runBlocking {
        if(getCategories().isEmpty()){
            addCategory("Plansze")
            addCategory("Pionki")
            addCategory("Akcesoria")
        }
    }
}