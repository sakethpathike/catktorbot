package com.sakethh.bot.twitter

import com.sakethh.redditData.fetchRandomPost
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.commons.io.FileUtils
import twitter4j.Twitter
import twitter4j.v1.StatusUpdate
import java.io.File
import java.io.InputStream

fun Routing.postNewTweet() {
    authenticate("botAuth") {
        get("/bot/newTweet") {
            val twitterBuilder = Twitter.newBuilder()
                .oAuthConsumer(System.getenv("oAuthConsumerKey"), System.getenv("oAuthConsumerSecret"))
                .oAuthAccessToken(System.getenv("oAccessTokenKey"), System.getenv("oAccessTokenSecret"))
                .build()
            val refFile = File("/src/main/assets/ref.jpg")
            val randomTweetData = fetchRandomPost()
            val refURL = Url(randomTweetData.data.url).toURI().toURL()
            FileUtils.copyURLToFile(refURL, refFile)
            val tweetTitle =
                "\"${randomTweetData.data.title}\"\n\nu/${randomTweetData.data.author} originally posted this on r/${randomTweetData.data.subreddit}\nhttps://reddit.com${randomTweetData.data.permalink}"
            val statusUpdate = StatusUpdate.of(tweetTitle).media(refFile)
            twitterBuilder.v1().tweets().updateStatus(statusUpdate)
            call.respond(tweetTitle)
        }
    }
}