package com.example.dao

import com.example.models.Categories
import com.example.models.Products
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val jdbcURL = "jdbc:sqlite:./build/db"
        val database = Database.connect(
            jdbcURL
        )
        transaction(database){
            SchemaUtils.create(Categories)
            SchemaUtils.create(Products)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}