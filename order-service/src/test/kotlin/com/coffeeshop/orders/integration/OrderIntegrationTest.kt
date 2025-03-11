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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class OrderIntegrationTest : StringSpec({

    val postgres = ContainersConfig.postgres
    val kafka = ContainersConfig.kafka

    fun ApplicationTestBuilder.setupEnvironment() {
        environment {
            val baseConfig = ApplicationConfig("application.yaml")
            config = MapApplicationConfig(
                "storage.jdbcURL" to postgres.jdbcUrl,
                "storage.user" to postgres.username,
                "storage.password" to postgres.password,
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
        }
    }

    "should verify created order" {
        testApplication {
            setupEnvironment()

            val getResponse = client.get("/orders")
            getResponse.status shouldBe HttpStatusCode.OK
            val orders = Json.decodeFromString<List<OrderDto>>(getResponse.bodyAsText())
            orders.any { it.coffeeType == "Espresso" && it.quantity == 2 } shouldBe true
        }
    }

    afterSpec {
        postgres.stop()
        kafka.stop()
    }
})
