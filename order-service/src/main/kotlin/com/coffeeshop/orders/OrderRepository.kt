package com.coffeeshop.orders

interface OrderRepository {
    fun getAllOrders(): List<OrderDto>
    fun createOrder(order: OrderCreateDto): String
}