package com.example.dao.Products

import com.example.models.Product

interface DAOFacadeProducts {

    //Products
    suspend fun getProducts(): List<Product>
    suspend fun getProductsByCategory(categoryReference: Int): List<Product>
    suspend fun getProduct(id: Int): Product?

    //Admin functions
    suspend fun addProduct(name: String, description: String, price: Double, categoryReference: Int): Product?
    suspend fun editProduct(id: Int, name: String,description: String,price: Double,categoryReference: Int): Boolean
    suspend fun deleteProduct(id: Int): Boolean

}