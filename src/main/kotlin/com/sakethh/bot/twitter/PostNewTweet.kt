package com.sakethh.bot.twitter

import com.sakethh.api.addNewPostToDB
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

fun Routing.postNewTweet() {
    authenticate("botAuth") {
        get("/bot/newTweet") {
            val twitterBuilder = Twitter.newBuilder()
                .oAuthConsumer(System.getenv("oAuthConsumerKey"), System.getenv("oAuthConsumerSecret"))
                .oAuthAccessToken(System.getenv("oAccessTokenKey"), System.getenv("oAccessTokenSecret"))
                .build()
            val refFile = File("/src/main/assets/ref.jpg")
            val fetchedTweetData = fetchRandomPost()
            val refURL = Url(fetchedTweetData.data.url).toURI().toURL()
            FileUtils.copyURLToFile(refURL, refFile)
            val tweetTitle =
                "\"${fetchedTweetData.data.title}\"\n\nu/${fetchedTweetData.data.author} originally posted this on r/${fetchedTweetData.data.subreddit}\nhttps://reddit.com${fetchedTweetData.data.permalink}"
            val statusUpdate = StatusUpdate.of(tweetTitle).media(refFile)
            twitterBuilder.v1().tweets().updateStatus(statusUpdate)
            val addToDB = addNewPostToDB(author = fetchedTweetData.data.author, is_video = fetchedTweetData.data.is_video, over_18 = fetchedTweetData.data.over_18, permalink = fetchedTweetData.data.permalink, subreddit = fetchedTweetData.data.subreddit, title = fetchedTweetData.data.title, url = fetchedTweetData.data.url)
            call.respond("$tweetTitle\n$addToDB")
        }
    }
}