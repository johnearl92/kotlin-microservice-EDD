val kafka_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val ktor_expose_version: String by project
val postgres_version: String by project
val hikari_connection_pool_version: String by project
val kotestVersion: String by project

plugins {
    application
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.0.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
}

group = "com.coffeeshop"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")


    implementation("org.jetbrains.exposed:exposed-core:$ktor_expose_version")
    implementation("org.jetbrains.exposed:exposed-dao:$ktor_expose_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$ktor_expose_version")
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("com.zaxxer:HikariCP:$hikari_connection_pool_version")

    implementation("org.apache.kafka:kafka-clients:$kafka_version")


    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-assertions-ktor:2.0.0")
    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:2.0.2")
    testImplementation("org.testcontainers:postgresql:1.19.7")
    testImplementation("org.testcontainers:kafka:1.19.7")

    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}