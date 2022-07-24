package com.example.routes

import com.example.dao.Products.daoProducts
import com.example.models.Product
import com.example.models.ReceiveProduct
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.productsRouting() {
    route("products") {
        get("") {
            call.respond(daoProducts.getProducts())
        }

        route("category/{id}"){
            get(""){
                val id = call.parameters.getOrFail("id").toInt()
                call.respond(daoProducts.getProductsByCategory(id))
            }
        }

        //debug/admin functionality

        post("/add") {
            val receiveProduct = call.receive<ReceiveProduct>()

            daoProducts.addProduct(
                receiveProduct.name,
                receiveProduct.description,
                receiveProduct.price,
                receiveProduct.categoryReference
            ) ?: return@post call.respondText("Product not added", status = HttpStatusCode.BadRequest)

            call.respondText(
                "Product created",
                status = HttpStatusCode.Accepted
            )

        }

        route("/{id}") {

            get("") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val product: Product = daoProducts.getProduct(id)
                    ?: return@get call.respondText("No Product with this number", status = HttpStatusCode.NotFound)
                call.respond(product)
            }

            put("") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val receiveProduct = call.receive<ReceiveProduct>()
                val success = daoProducts.editProduct(
                    id,
                    receiveProduct.name,
                    receiveProduct.description,
                    receiveProduct.price,
                    receiveProduct.categoryReference
                )
                if (success) {
                    call.respondText(
                        "Product edited",
                        status = HttpStatusCode.Accepted
                    )
                } else {
                    call.respondText(
                        "Product not edited",
                        status = HttpStatusCode.NotFound
                    )
                }
            }

            delete("") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val sucess = daoProducts.deleteProduct(id)
                if (sucess) {
                    call.respondText(
                        "Product deleted",
                        status = HttpStatusCode.Accepted
                    )
                } else {
                    call.respondText(
                        "No Product deletion happened",
                        status = HttpStatusCode.NotFound
                    )
                }
            }
        }
    }
}