package com.coffeeshop

import com.coffeeshop.orders.OrderTable
import com.coffeeshop.orders.mongo.OrderDocument
import com.mongodb.client.MongoClients
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases(config: ApplicationConfig) {
    // SQL
    val driver = config.property("storage.driver").getString()
    val url = config.property("storage.jdbcURL").getString()
    val user = config.property("storage.user").getString()
    val pass = config.property("storage.password").getString()

    val hikariConfig = HikariConfig().apply {
        jdbcUrl = url
        driverClassName = driver
        username = user
        password = pass
        maximumPoolSize = 5
    }

    //Mongo
    val databaseName = config.property("storage.mongo.database").getString()
    val uri = config.property("storage.mongo.uri").getString()

    val mongoClient = MongoClients.create(uri)
    val database = mongoClient.getDatabase(databaseName)


    try {
        val dataSource = HikariDataSource(hikariConfig)
        Database.connect(dataSource)
        initializeDatabase()

        OrderDocument.initialize(database)
    } catch (e: Exception) {
        log.error("Failed to connect to the database", e)
    }
}

private fun initializeDatabase() {
    transaction {
        SchemaUtils.create(OrderTable)
    }
}