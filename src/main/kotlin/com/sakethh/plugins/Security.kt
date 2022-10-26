package com.sakethh.plugins

import dev.forst.ktor.apikey.apiKey
import io.ktor.server.application.*
import io.ktor.server.auth.*
import org.litote.kmongo.KMongo
import org.litote.kmongo.find

data class APIKEY(val apiKey: String) : Principal
data class BotKey(val apiKey: String) : Principal

fun Application.configureSecurity() {
    install(Authentication) {
        apiKey("apiKey") {
            validate { keyFromHeader ->
                val kMongo = KMongo.createClient(System.getenv("MONGODB_URL"))
                val collectionData =
                    kMongo.getDatabase(System.getenv("DB_NAME")).getCollection(System.getenv("API_COLLECTION_NAME"))
                val expectedKey = collectionData.find("""{bearerToken:"$keyFromHeader"}""").count()
                if (expectedKey != 0) {
                    APIKEY(keyFromHeader)
                } else {
                    null
                }
            }
        }
        apiKey("botAuth") {
            validate { keyFromHeader ->
                val kMongo = KMongo.createClient(System.getenv("MONGODB_URL"))
                val collectionData =
                    kMongo.getDatabase(System.getenv("DB_NAME")).getCollection(System.getenv("BOT_AUTH"))
                val expectedKey = collectionData.find("""{_id:"botKey"}""").toList()[0].getString("botKey")
                if (expectedKey == keyFromHeader) {
                    BotKey(keyFromHeader)
                } else {
                    null
                }
            }
        }
    }
}
