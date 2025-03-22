package com.coffeeshop.orders

import org.jetbrains.exposed.sql.*

object OrderTable : Table("orders") {
    val id = varchar("uuid", 255)
    val coffeeType = varchar("coffee_type", 255)
    val quantity = integer("quantity")
    val status = enumerationByName("status", 50, OrderStatus::class).default(OrderStatus.PENDING)
    override val primaryKey = PrimaryKey(id)
}

enum class OrderStatus {
    PENDING
}