package com.coffeeshop.orders

import org.jetbrains.exposed.sql.*

object Orders : Table("orders") {
    val id = integer("id").autoIncrement()
    val coffeeType = varchar("coffee_type", 255)
    val quantity = integer("quantity")
    val uuid = varchar("uuid", 255)
    override val primaryKey = PrimaryKey(id)
}