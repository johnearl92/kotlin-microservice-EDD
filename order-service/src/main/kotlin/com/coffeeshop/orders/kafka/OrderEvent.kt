package com.coffeeshop.orders.kafka

import kotlinx.serialization.Serializable

@Serializable
data class OrderEvent(
    val uuid: String,
    val coffeeType: String,
    val quantity: Int
)

