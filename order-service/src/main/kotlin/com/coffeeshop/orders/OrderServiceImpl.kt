package com.coffeeshop.orders

import com.coffeeshop.orders.kafka.OrderProducer

class OrderServiceImpl(private val orderRepository: OrderRepository) : OrderService {
    override suspend fun getOrder(id:String): OrderDto {
        return orderRepository.getOrder(id)
    }

    override fun createOrder(orderCreateDto: OrderCreateDto): String {
        val uuid = orderRepository.createOrder(orderCreateDto)
        OrderProducer.sendOrderCreatedEvent(orderCreateDto.toOrderPlacedEvent(uuid))
        return uuid
    }
}