package com.sakethh.plugins

import com.sakethh.api.apiRoutings
import com.sakethh.bot.botRoutings
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.reflect.*
import java.io.File

fun Application.configureRouting() {
    routing {
        apiRoutings()
        botRoutings()
        get("/"){
            call.respondRedirect("https://github.com/sakethpathike/catktorbot")
        }
    }
}