package com.coffeeshop

import com.coffeeshop.orders.Orders
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases(config: ApplicationConfig) {
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

    try {
        val dataSource = HikariDataSource(hikariConfig)
        Database.connect(dataSource)
        initializeDatabase()
    } catch (e: Exception) {
        log.error("Failed to connect to the database", e)
    }
}

private fun initializeDatabase() {
    transaction {
        SchemaUtils.create(Orders)
    }
}