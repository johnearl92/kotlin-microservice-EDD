package com.coffeeshop.orders

import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    val id: String,
    val coffeeType: String,
    val quantity: Int,
    val status: OrderStatus
)
