package com.example.dao.Categories

import com.example.models.Category

interface DAOFacadeCategories {

    //Categories
    suspend fun getCategories(): List<Category>
    suspend fun getCategory(id: Int): Category?
    //Admin functions
    suspend fun addCategory(name: String): Category?
    suspend fun editCategory(id: Int, name: String): Boolean
    suspend fun deleteCategory(id: Int): Boolean

}