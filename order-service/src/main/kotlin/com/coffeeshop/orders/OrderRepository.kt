package com.coffeeshop.orders

interface OrderRepository {
    fun getAllOrders(): List<OrderDto>
    fun addOrder(order: OrderCreateDto): String
}