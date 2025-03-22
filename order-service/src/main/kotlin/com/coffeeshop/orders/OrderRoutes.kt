package com.coffeeshop.orders

import com.coffeeshop.orders.kafka.OrderProducer
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*

val orderRepo = OrderRepositoryImpl()

fun Route.orderRoutes() {
    route("/orders") {
        get {
            call.respond(orderRepo.getAllOrders())
        }
        post {
            try {
                val orderCreateDto = call.receive<OrderCreateDto>()
                val uuid = orderRepo.createOrder(orderCreateDto)
                OrderProducer.sendOrderCreatedEvent(orderCreateDto.toOrderPlacedEvent(uuid))
                call.respond(HttpStatusCode.Created, mapOf("uuid" to uuid, "order" to orderCreateDto))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Failed to create order")
            }
        }
    }
}
