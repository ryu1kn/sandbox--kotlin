package org.example

import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import java.net.URI

fun main() = runBlocking<Unit> {
    println("Hello World!")

    val repo = BookRepo()
    repo.put(Book("foo", "Awesome book"))
    println(repo.get("foo"))
}

class BookRepo {
    private val dynamoDbClient = DynamoDbAsyncClient.builder()
        .apply { endpointOverride(URI("http://localhost:8000")) }
        .region(Region.AP_SOUTHEAST_2)
        .build()
    private val tableName = "books"
    private val tableKey = "id"

    suspend fun get(key: String): Book =
        dynamoDbClient.getItem(makeGetItemReq(key)).await().item().toBook()

    suspend fun put(book: Book) {
        dynamoDbClient.putItem(makePutItemReq(book)).await()
    }

    private fun makeGetItemReq(key: String) =
        GetItemRequest.builder().tableName(tableName).key(key.toKeyMap()).build()

    private fun makePutItemReq(book: Book) =
        PutItemRequest.builder().tableName(tableName).item(book.toAttributeMap()).build()

    private fun String.toKeyMap() = mapOf(tableKey to this.toAttributeValue())

    private fun Book.toAttributeMap() = mapOf(
        tableKey to id.toAttributeValue(),
        "title" to name.toAttributeValue()
    )

    private fun Map<String, AttributeValue>.toBook() =
        Book(get(tableKey)!!.s(), get("title")!!.s())

    private fun String.toAttributeValue() =
        AttributeValue.builder().s(this).build()
}

data class Book(val id: String, val name: String)
