package com.example.plugins

import com.example.routes.categoriesRouting
import com.example.routes.productsRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        categoriesRouting()
        productsRouting()
    }
}
