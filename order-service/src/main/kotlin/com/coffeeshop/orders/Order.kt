package com.coffeeshop.orders

import com.coffeeshop.orders.kafka.OrderPlacedEvent
import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String,
    val coffeeType: String,
    val quantity: Int,
    val status: OrderStatus
) {
    fun toOrderPlacedEvent(): OrderPlacedEvent {
        return OrderPlacedEvent(
            uuid = this.id,
            coffeeType = this.coffeeType,
            quantity = this.quantity,
            status = this.status
        )
    }

    fun toOrderDto(): OrderDto {
        return OrderDto(
            id = this.id,
            coffeeType = this.coffeeType,
            quantity = this.quantity,
            status = this.status
        )
    }
}