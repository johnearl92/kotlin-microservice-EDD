package com.coffeeshop.orders.kafka

import com.coffeeshop.orders.OrderCreateDto
import io.ktor.server.config.*
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import kotlinx.serialization.json.Json
import org.apache.kafka.clients.producer.ProducerConfig
import java.util.*
import java.time.Duration

object OrderConsumer {
    private const val TOPIC = "orders"
    private lateinit var consumer: KafkaConsumer<String, String>

    fun initialize(config: ApplicationConfig) {
        val host = config.property("kafka.host").getString()
        consumer = KafkaConsumer<String, String>(Properties().apply {
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, host)
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            put(ConsumerConfig.GROUP_ID_CONFIG, "order-consumer-group")
            put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        })
    }

    fun start() {
        consumer.subscribe(listOf(TOPIC))

        while (true) {
            val records = consumer.poll(Duration.ofMillis(100))
            for (record in records) {
                val orderPlacedEvent = Json.decodeFromString<OrderPlacedEvent>(record.value())


            }
        }
    }
}