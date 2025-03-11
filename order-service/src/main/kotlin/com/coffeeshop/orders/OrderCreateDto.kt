package com.coffeeshop.orders

import com.coffeeshop.orders.kafka.OrderEvent
import kotlinx.serialization.Serializable

@Serializable
data class OrderCreateDto(
    val coffeeType: String,
    val quantity: Int
)

fun OrderCreateDto.toOrderEvent(uuid: String): OrderEvent {
    return OrderEvent(
        uuid = uuid,
        coffeeType = this.coffeeType,
        quantity = this.quantity
    )
}