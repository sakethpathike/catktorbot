package com.sakethh.api

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.KMongo
import org.litote.kmongo.MongoOperator
import org.litote.kmongo.find
import org.litote.kmongo.json

fun Routing.previousPosts() {
    authenticate("apiKey") {
        get("/previousPosts") {
            val kMongo = KMongo.createClient(System.getenv("MONGODB_URL"))
            val collectionData = kMongo.getDatabase(System.getenv("DB_NAME")).getCollection(System.getenv("PREVIOUS_POSTS"))
            val previousPostsData = collectionData.find("""{},{_id:0}""").toList()
            call.respond(previousPostsData.json).also { kMongo.close() }
        }
    }
    authenticate("apiKey") {
        get("/previousPosts/{requestedDate}") {
            val requestedDate = call.response.call.parameters["requestedDate"]
            val kMongo = KMongo.createClient(System.getenv("MONGODB_URL"))
            val collectionData = kMongo.getDatabase(System.getenv("DB_NAME")).getCollection(System.getenv("PREVIOUS_POSTS"))
            val previousPostsData = collectionData.find("""{postedByBotOn:{${MongoOperator.regex}:/$requestedDate/i}},{_id:0}""").toList()
            call.respond(previousPostsData.json).also { kMongo.close() }
        }
    }
}