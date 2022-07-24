package com.example.dao.Products

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.Product
import com.example.models.Products
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*

class DAOFacadeProductsImpl : DAOFacadeProducts {

    private fun resultRowToProduct(row: ResultRow) = Product(
        id = row[Products.id],
        name = row[Products.name],
        description = row[Products.description],
        price = row[Products.price],
        categoryReference = row[Products.category_reference]
    )

    override suspend fun getProducts(): List<Product> = dbQuery {
        Products.selectAll().map(::resultRowToProduct)
    }

    override suspend fun getProductsByCategory(categoryReference: Int): List<Product> = dbQuery {
        Products
            .select{Products.category_reference eq categoryReference}
            .map(::resultRowToProduct)
    }

    override suspend fun getProduct(id: Int): Product? = dbQuery {
        Products
            .select{Products.id eq id}
            .map(::resultRowToProduct)
            .singleOrNull()
    }

    override suspend fun addProduct(
        name: String,
        description: String,
        price: Double,
        categoryReference: Int
    ): Product? = dbQuery {
        val insertStatement = Products.insert {
            it[Products.name] = name
            it[Products.description] = description
            it[Products.price] = price
            it[category_reference] = categoryReference
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToProduct)
    }

    override suspend fun editProduct(
        id: Int,
        name: String,
        description: String,
        price: Double,
        categoryReference: Int
    ): Boolean = dbQuery{
        Products.update({ Products.id eq id }) {
            it[Products.name] = name
            it[Products.description] = description
            it[Products.price] = price
            it[category_reference] = categoryReference
        } > 0
    }

    override suspend fun deleteProduct(id: Int): Boolean = dbQuery {
        Products.deleteWhere { Products.id eq id } > 0
    }
}

val daoProducts : DAOFacadeProducts = DAOFacadeProductsImpl().apply {
    runBlocking {
        if(getProducts().isEmpty()){
            addProduct("Plansza do gry w kolko i krzyzyk", "Niezwykla plansza do gry w kolko i krzyzyk (kolka i krzyzyki sprzedawane oddzielnie)", 25.99, 1)
            addProduct("Kolko", "Kolko do gry w kolko i krzyzyk (Niezbedne do gry w kolko i krzyzyk)", 0.99, 2)
            addProduct("Krzyzyk", "Krzyzyk do gry w kolko i krzyzyk (Niezbedne do gry w kolko i krzyzyk)", 0.99, 2)

            addProduct("Plansza do gry w chinczyka", "Niezwykla plansza do gry w chinczyka (pionki i kosci sprzedawane oddzielnie)",15.99,1)
            addProduct("Zielony pionek do gry w chinczyka", "Pionek do gry w chinczyka - kolor zielony (Niezbedne do gry w chinczyka)", 0.50, 2)
            addProduct("Czerwony pionek do gry w chinczyka", "Pionek do gry w chinczyka - kolor czerwony (Niezbedne do gry w chinczyka)", 0.50, 2)
            addProduct("Zolty pionek do gry w chinczyka", "Pionek do gry w chinczyka - kolor zolty (Niezbedne do gry w chinczyka)", 0.50, 2)
            addProduct("Niebieski pionek do gry w chinczyka", "Pionek do gry w chinczyka - kolor niebieski (Niezbedne do gry w chinczyka)", 0.50, 2)
            addProduct("Fioletowy pionek do gry w chinczyka", "Unikatowy pionek do gry w chinczyka - kolor fioletowy (Niezbedne do gry w chinczyka)", 10.00, 2)

            addProduct("Kostka (6-scian, D6) do gry w chinczyka", "Kostka do gry (Niezbedne do gry w chinczyka)", 1.0, 3)
        }
    }
}