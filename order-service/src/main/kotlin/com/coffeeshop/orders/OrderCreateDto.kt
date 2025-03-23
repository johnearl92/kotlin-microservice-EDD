package com.coffeeshop.orders

import com.coffeeshop.orders.kafka.OrderPlacedEvent
import kotlinx.serialization.Serializable

@Serializable
data class OrderCreateDto(
    val coffeeType: String,
    val quantity: Int
) {
    fun toOrderDto(uuid: String): OrderDto {
        return OrderDto(
            id = uuid,
            coffeeType = this.coffeeType,
            quantity = this.quantity,
            status = OrderStatus.PENDING
        )
    }
}

fun OrderCreateDto.toOrderPlacedEvent(uuid: String): OrderPlacedEvent {
    return OrderPlacedEvent(
        uuid = uuid,
        coffeeType = this.coffeeType,
        quantity = this.quantity,
        status = OrderStatus.PENDING
    )
}