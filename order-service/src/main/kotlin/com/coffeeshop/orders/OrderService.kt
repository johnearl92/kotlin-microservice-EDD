package com.coffeeshop.orders

interface OrderService {
    suspend fun getOrder(id: String): OrderDto
    fun createOrder(orderCreateDto: OrderCreateDto): OrderDto
}