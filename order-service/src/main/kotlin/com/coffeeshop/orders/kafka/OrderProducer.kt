package com.coffeeshop.orders.kafka

import io.ktor.server.config.*
import kotlinx.serialization.encodeToString
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import kotlinx.serialization.json.Json
import java.util.*

object OrderProducer {
    private const val TOPIC = "orders"
    private lateinit var producer: KafkaProducer<String, String>


    fun initialize(config: ApplicationConfig) {
        val host = config.property("kafka.host").getString()
        producer = KafkaProducer<String, String>(Properties().apply {
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, host)
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
            put(ProducerConfig.ACKS_CONFIG, "all")
        })
    }

    fun sendOrderCreatedEvent(order: OrderPlacedEvent) {
        val message = Json.encodeToString(order)
        val record = ProducerRecord(TOPIC, order.uuid, message)
        producer.send(record)
        println("✅ Sent OrderCreated event: $message")
    }
}