package com.coffeeshop.orders

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class OrderRepositoryImpl: OrderRepository {

    override fun createOrder(order: OrderCreateDto): String = transaction {
        OrderTable.insert {
            it[id] = java.util.UUID.randomUUID().toString()
            it[coffeeType] = order.coffeeType
            it[quantity] = order.quantity
            it[status] = OrderStatus.PENDING
        } get OrderTable.id
    }

    override fun getAllOrders(): List<OrderDto> = transaction {
        OrderTable.selectAll().map {
            OrderDto(
                coffeeType = it[OrderTable.coffeeType],
                quantity = it[OrderTable.quantity]
            )
        }
    }


}