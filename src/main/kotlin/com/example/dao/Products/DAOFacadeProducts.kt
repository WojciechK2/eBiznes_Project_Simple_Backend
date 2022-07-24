package com.example.dao.Products

import com.example.models.Product

interface DAOFacadeProducts {

    //Products
    suspend fun getProducts(): List<Product>
    suspend fun getProductsByCategory(category_reference: Int): List<Product>
    suspend fun getProduct(id: Int): Product?

    //Admin functions
    suspend fun addProduct(name: String, description: String, price: Double, category_reference: Int): Product?
    suspend fun editProduct(id: Int, name: String,description: String,price: Double,category_reference: Int): Boolean
    suspend fun deleteProduct(id: Int): Boolean

}