package com.coffeeshop.orders

import org.jetbrains.exposed.sql.Table

object OrderTable : Table("orders") {
    val id = uuid("id")
    val coffeeType = varchar("coffee_type", 255)
    val quantity = integer("quantity")
    val status = enumerationByName("status", 50, OrderStatus::class).default(OrderStatus.PENDING)
    override val primaryKey = PrimaryKey(id)
}

enum class OrderStatus {
    PENDING
}