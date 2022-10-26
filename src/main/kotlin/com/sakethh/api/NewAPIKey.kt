package com.sakethh.api

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.date.*
import kotlinx.coroutines.coroutineScope
import org.litote.kmongo.KMongo
import org.litote.kmongo.find
import org.litote.kmongo.insertOne

fun Routing.newApiKey() {
    get("/newApiKey") {
        call.respond(createNewIP(call.request.origin.remoteHost))
    }
}

suspend fun doesIPExists(clientIP:String): Boolean {
    val kMongo = KMongo.createClient(System.getenv("MONGODB_URL"))
    val client = HttpClient(CIO)
    var ipCount: Int
    coroutineScope {
        val collectionData = kMongo.getDatabase(System.getenv("DB_NAME")).getCollection(System.getenv("API_COLLECTION_NAME"))
        ipCount = collectionData.find("""{ip:"$clientIP"}""").count()
    }
    return ipCount != 0.also {
        kMongo.close()
        client.close()
    }
}

suspend fun createNewIP(clientIP:String): String {
    val doesIPExists = doesIPExists(clientIP =clientIP)
    val ktorClient = HttpClient(CIO)
    val kMongo = KMongo.createClient(System.getenv("MONGODB_URL"))
    val collectionData = kMongo.getDatabase(System.getenv("DB_NAME")).getCollection(System.getenv("API_COLLECTION_NAME"))
    val bearerToken: String = if (!doesIPExists) {
        val currentDate = GMTDate().toHttpDate()
        val newToken = generatedNewToken()
        val addToken = collectionData.insertOne("{ip:'$clientIP', bearerToken:'$newToken',dateCreated:'$currentDate'}")
        if (addToken.wasAcknowledged()) {
            "your bearer token is: \n$newToken\ndon't share your token with anyone, as it gives access to the API on behalf you. Your ip address $clientIP is recorded in our database"
        } else {
            "something went wrong from our side, please report it."
        }
    } else {
        val existingToken = collectionData.find("""{ip:"$clientIP"}""").toList()[0].getString("bearerToken")
        val dateCreated = collectionData.find("""{ip:"$clientIP"}""").toList()[0].getString("dateCreated")
        "you already have a token which was created on $dateCreated. Your token:\n$existingToken"
    }
    return bearerToken.also {
        kMongo.close()
        ktorClient.close()
    }
}

fun generateToken(): String {
    val randomData = ('a'..'z') + ('A'..'Z') + (0..9)
    return randomData.shuffled().take(32).joinToString("")
}

fun generatedNewToken(): String {
    val kMongo = KMongo.createClient(System.getenv("MONGODB_URL"))
    val collectionData = kMongo.getDatabase(System.getenv("DB_NAME")).getCollection(System.getenv("API_COLLECTION_NAME"))
    val generatedToken = generateToken()
    val currentTokenCount = collectionData.find("""{bearerToken:"$generatedToken"}""").count()
    return if(currentTokenCount==0){
        generatedToken
    }else{
        generateToken()
    }.also { kMongo.close() }
}