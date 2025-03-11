package com.coffeeshop.orders

import com.coffeeshop.orders.Orders.uuid
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class OrderRepositoryImpl: OrderRepository {

    override fun addOrder(order: OrderCreateDto): String = transaction {
        Orders.insert {
            it[coffeeType] = order.coffeeType
            it[quantity] = order.quantity
            it[uuid] = java.util.UUID.randomUUID().toString()
        } get uuid
    }

    override fun getAllOrders(): List<OrderDto> = transaction {
        Orders.selectAll().map {
            OrderDto(
                coffeeType = it[Orders.coffeeType],
                quantity = it[Orders.quantity]
            )
        }
    }


}