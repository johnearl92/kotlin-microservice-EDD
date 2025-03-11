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
            val orderCreateDto = call.receive<OrderCreateDto>()
            val uuid = orderRepo.addOrder(orderCreateDto)
            OrderProducer.sendOrderCreatedEvent(orderCreateDto.toOrderEvent(uuid))
            call.respond(HttpStatusCode.Created, orderCreateDto)
        }
    }
}
