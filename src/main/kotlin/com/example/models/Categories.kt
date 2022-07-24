package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Category(val id: Int, val name: String)

@Serializable
data class ReceiveCategory(val name: String)

object Categories : Table(){
    val id = integer("id").autoIncrement().uniqueIndex()
    val name = varchar("name", 128).uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}