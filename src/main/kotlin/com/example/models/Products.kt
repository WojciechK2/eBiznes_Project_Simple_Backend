package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Product(val id: Int, val name: String, val description: String, val price: Double, val categoryReference: Int)

@Serializable
data class ReceiveProduct(val name: String, val description: String, val price: Double, val categoryReference: Int)

object Products : Table() {
    val id = integer("id").autoIncrement().uniqueIndex()
    val name = varchar("name", 128)
    val description = varchar("description", 1024)
    val price = double("price")
    val category_reference = integer("category_id").references(Categories.id)

    override val primaryKey = PrimaryKey(id)
}