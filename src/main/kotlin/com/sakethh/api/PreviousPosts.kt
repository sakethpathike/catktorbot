package com.sakethh.api

import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Routing.previousPosts() {
    authenticate("apiKey") {
        get("/previousPosts") {

        }
    }
}
