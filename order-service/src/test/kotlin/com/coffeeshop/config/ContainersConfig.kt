package com.coffeeshop.com.coffeeshop.config

import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

object ContainersConfig {
    val postgres: PostgreSQLContainer<Nothing> = PostgreSQLContainer<Nothing>("postgres:latest").apply {
        withDatabaseName("order_service")
        withUsername("postgres")
        withPassword("password")
        start()
    }

    val kafka: KafkaContainer = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest")).apply {
        withEmbeddedZookeeper()
        start()
    }

    val mongo: MongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:6.0.6")).apply {
        start()
    }
}