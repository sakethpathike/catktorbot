package com.sakethh.redditData

import com.sakethh.redditData.jsonDispatch.Children
import com.sakethh.redditData.jsonDispatch.RedditData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import org.litote.kmongo.KMongo
import org.litote.kmongo.MongoOperator
import org.litote.kmongo.find
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

suspend fun fetchRandomPost(): Children {
    val kMongo = KMongo.createClient(System.getenv("MONGODB_URL"))
    val collectionData = kMongo.getDatabase(System.getenv("DB_NAME")).getCollection(System.getenv("SUBREDDIT_URLS"))
    val listOfSubredditURLs = mutableListOf<String>()
    collectionData.find("""{url:{${MongoOperator.regex}:/www.reddit.com/i}}""").toList().forEach { document ->
        listOfSubredditURLs.add(document.getString("url"))
    }
    val pickedSubredditURL = listOfSubredditURLs.random()
    val ktorClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
    val fetchDataFromURL = ktorClient.get(pickedSubredditURL).body<RedditData>()
    val fetchedDataFromURL = mutableListOf<Children>()
    for(children in fetchDataFromURL.data.children){
       if(!children.data.is_video && children.data.url.contains(regex = Regex("/i.redd.it")) && !children.data.over_18){
           fetchedDataFromURL.add(children)
       }
    }
    val randomObject = fetchedDataFromURL.random()
    val randomObjectCheckingFromDB = collectionData.find("""{permalink:"${randomObject.data.permalink}"}""").count()
    return if(randomObjectCheckingFromDB == 0){
        randomObject
    }else{
        fetchedDataFromURL.random()
    }
}