package com.coffeeshop.orders.mongo

import com.coffeeshop.orders.OrderDto
import com.coffeeshop.orders.OrderStatus
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.Document
import org.bson.types.ObjectId

@Serializable
data class Order(
    val orderId: String,
    val coffeeType: String,
    val quantity: Int,
    val status: OrderStatus
) {
    fun toOrderDto(): OrderDto  = OrderDto(
        coffeeType = this.coffeeType,
        quantity = this.quantity,
        status = this.status,
        id = this.orderId,
    )

    fun toDocument(): Document = Document.parse(Json.encodeToString(this))


    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun fromDocument(document: Document): Order = json.decodeFromString(document.toJson())

    }
}

object OrderDocument {

    lateinit var collection: MongoCollection<Document>

    fun initialize(database: MongoDatabase) {
        if (!::collection.isInitialized) {
            database.createCollection("orders")
            collection = database.getCollection("orders")
        }
    }

    suspend fun create(order: Order): String = withContext(Dispatchers.IO) {
        val doc = order.toDocument()
        collection.insertOne(doc)
        doc["_id"].toString()
    }

    suspend fun read(id: String): Order? = withContext(Dispatchers.IO) {
        collection.find(Filters.eq("orderId", id)).first()?.let(Order::fromDocument)
    }
}