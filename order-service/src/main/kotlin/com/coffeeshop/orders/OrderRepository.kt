package com.coffeeshop.orders

interface OrderRepository {
    suspend fun getOrder(id:String): OrderDto
    fun createOrder(order: Order): Order
}