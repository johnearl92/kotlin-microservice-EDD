package com.coffeeshop.orders

import com.coffeeshop.orders.mongo.OrderDocument
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

    override suspend fun getOrder(id:String): OrderDto {
        return OrderDocument.read(id)?.toOrderDto() ?: throw NoSuchElementException("Order not found")
    }


}