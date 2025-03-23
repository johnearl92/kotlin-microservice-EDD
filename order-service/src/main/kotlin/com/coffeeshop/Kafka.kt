package com.coffeeshop

import com.coffeeshop.orders.kafka.OrderConsumer
import com.coffeeshop.orders.kafka.OrderProducer
import io.ktor.server.application.*
import io.ktor.server.config.*
import kotlinx.coroutines.launch

fun Application.configureKafka(config: ApplicationConfig) {
    OrderProducer.initialize(config)
    OrderConsumer.initialize(config)

    launch {
        OrderConsumer.start()
    }
}
