package com.coffeeshop.orders

import com.coffeeshop.orders.mongo.OrderDocument
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import kotlin.NoSuchElementException

class OrderRepositoryImpl: OrderRepository {

    override fun createOrder(order: Order): Order = transaction {
        val resultRow = OrderTable.insert {
            it[id] = UUID.fromString(order.id)
            it[coffeeType] = order.coffeeType
            it[quantity] = order.quantity
            it[status] = OrderStatus.PENDING
        }.resultedValues?.singleOrNull() ?: error("Insert order failed")

        resultRow.toOrder()
    }

    override suspend fun getOrder(id:String): OrderDto {
        return OrderDocument.read(id)?.toOrderDto() ?: throw NoSuchElementException("Order not found")
    }

    fun ResultRow.toOrder(): Order = Order(
        id = this[OrderTable.id].toString(),
        coffeeType = this[OrderTable.coffeeType],
        quantity = this[OrderTable.quantity],
        status = this[OrderTable.status]
    )


}