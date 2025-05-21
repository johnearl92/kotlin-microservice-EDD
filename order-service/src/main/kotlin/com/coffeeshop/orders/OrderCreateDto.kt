package com.coffeeshop.orders

import kotlinx.serialization.Serializable

@Serializable
data class OrderCreateDto(
    val coffeeType: String,
    val quantity: Int
) {
}

fun OrderCreateDto.toOrder(): Order {
    return Order(
        id = java.util.UUID.randomUUID().toString(),
        coffeeType = this.coffeeType,
        quantity = this.quantity,
        status = OrderStatus.PENDING
    )
}