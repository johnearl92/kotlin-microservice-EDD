package com.coffeeshop.orders

import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    val coffeeType: String,
    val quantity: Int
)
