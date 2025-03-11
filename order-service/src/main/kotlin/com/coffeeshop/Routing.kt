package com.coffeeshop

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import com.coffeeshop.orders.orderRoutes

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        orderRoutes()
    }
}
