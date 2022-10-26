package com.sakethh.api

import io.ktor.http.*
import io.ktor.util.date.*
import org.litote.kmongo.KMongo
import org.litote.kmongo.insertOne

fun addNewPostToDB(
    author: String,
    is_video: Boolean,
    over_18: Boolean,
    permalink: String,
    subreddit: String,
    title: String,
    url: String
): String {
    val kMongo = KMongo.createClient(System.getenv("MONGODB_URL"))
    val collectionData = kMongo.getDatabase(System.getenv("DB_NAME")).getCollection(System.getenv("PREVIOUS_POSTS"))
    val postedByBotOn = "${GMTDate().dayOfMonth}${GMTDate().month}${GMTDate().year}"
    val postedByBotOnHttpFormat = GMTDate().toHttpDate()
    val addToDB = collectionData.insertOne("{author: '$author', isVideo: '$is_video', over18: '$over_18', permalink: '$permalink', subreddit: '$subreddit', title: '$title',imgURL: '$url', postedByBotOn: '$postedByBotOn', postedByBotOnHTTPFormat: '$postedByBotOnHttpFormat'}")
    val wasAcknowledged: String = if(addToDB.wasAcknowledged()){
        "post added to database"
    }else{
        "something went wrong"
    }
    return wasAcknowledged.also { kMongo.close() }
}