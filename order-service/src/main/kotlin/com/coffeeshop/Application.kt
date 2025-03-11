package com.coffeeshop

import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
    configureDatabases(environment.config)
    configureKafka(environment.config)
}
