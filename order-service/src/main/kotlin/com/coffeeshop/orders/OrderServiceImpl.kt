package com.coffeeshop.orders

import com.coffeeshop.orders.kafka.OrderProducer

class OrderServiceImpl(private val orderRepository: OrderRepository) : OrderService {
    override suspend fun getOrder(id:String): OrderDto {
        return orderRepository.getOrder(id)
    }

    override fun createOrder(orderCreateDto: OrderCreateDto): OrderDto {
        val order = orderRepository.createOrder(orderCreateDto.toOrder())
        OrderProducer.sendOrderCreatedEvent(order.toOrderPlacedEvent())
        return order.toOrderDto()
    }
}