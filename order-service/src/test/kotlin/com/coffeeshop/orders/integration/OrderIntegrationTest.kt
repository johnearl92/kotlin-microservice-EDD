package com.coffeeshop.com.coffeeshop.orders.integration

import com.coffeeshop.com.coffeeshop.config.ContainersConfig
import com.coffeeshop.orders.OrderCreateDto
import com.coffeeshop.orders.OrderDto
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class OrderIntegrationTest : StringSpec({

    val postgres = ContainersConfig.postgres
    val kafka = ContainersConfig.kafka
    val mongo = ContainersConfig.mongo
    var createdOrderId: String? = null

    fun ApplicationTestBuilder.setupEnvironment() {
        environment {
            val baseConfig = ApplicationConfig("application.yaml")
            config = MapApplicationConfig(
                "storage.jdbcURL" to postgres.jdbcUrl,
                "storage.user" to postgres.username,
                "storage.password" to postgres.password,
                "storage.mongo.uri" to mongo.connectionString,
                "kafka.host" to kafka.bootstrapServers
            ).withFallback(baseConfig)
        }
    }


    "should create order" {
        testApplication {
            setupEnvironment()

            val order = OrderCreateDto("Espresso", 2)
            val response = client.post("/orders") {
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString(order))
            }
            response.status shouldBe HttpStatusCode.Created
            val createdOrder = Json.decodeFromString<OrderDto>(response.bodyAsText())
            createdOrderId = createdOrder.id
        }
    }

    "should verify created order" {
        testApplication {
            setupEnvironment()
            launch {
                // Wait for the OrderConsumer to process the order
                delay(1000)
                val getResponse = client.get("/orders/${createdOrderId}")
                getResponse.status shouldBe HttpStatusCode.OK
                val order = Json.decodeFromString<OrderDto>(getResponse.bodyAsText())
                order.coffeeType shouldBe "Espresso"
                order.quantity shouldBe 2
            }
        }
    }

    afterSpec {
        postgres.stop()
        kafka.stop()
        mongo.stop()
    }
})
