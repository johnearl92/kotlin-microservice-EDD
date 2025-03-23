package com.coffeeshop.orders

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import org.slf4j.LoggerFactory

val orderService = OrderServiceImpl(OrderRepositoryImpl())
private val logger = LoggerFactory.getLogger("OrderRoutes")

fun Route.orderRoutes() {
    route("/orders") {
        get("/{id}") {
            try {

                val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
                call.respond(orderService.getOrder(id))
            } catch (e: Exception) {
                logger.error("Failed to get order", e)
                call.respond(HttpStatusCode.NotFound, "Order not found")
            }
        }
        post {
            try {
                val orderCreateDto = call.receive<OrderCreateDto>()
                val uuid = orderService.createOrder(orderCreateDto)
                call.respond(HttpStatusCode.Created, orderCreateDto.toOrderDto(uuid))
            } catch (e: Exception) {
                logger.error("Failed to get order", e)
                call.respond(HttpStatusCode.InternalServerError, "Failed to create order")
            }
        }
    }
}
