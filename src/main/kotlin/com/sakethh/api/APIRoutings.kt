package com.sakethh.api

import io.ktor.server.routing.*

fun Routing.apiRoutings(){
   newApiKey()
   previousPosts()
}