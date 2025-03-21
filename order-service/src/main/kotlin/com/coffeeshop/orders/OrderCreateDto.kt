package com.coffeeshop.orders

import com.coffeeshop.orders.kafka.OrderPlacedEvent
import kotlinx.serialization.Serializable

@Serializable
data class OrderCreateDto(
    val coffeeType: String,
    val quantity: Int
)

fun OrderCreateDto.toOrderEvent(uuid: String): OrderPlacedEvent {
    return OrderPlacedEvent(
        uuid = uuid,
        coffeeType = this.coffeeType,
        quantity = this.quantity
    )
}