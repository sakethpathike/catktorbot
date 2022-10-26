package com.sakethh.bot

import com.sakethh.bot.twitter.postNewTweet
import io.ktor.server.routing.*

fun Routing.botRoutings(){
    postNewTweet()
}