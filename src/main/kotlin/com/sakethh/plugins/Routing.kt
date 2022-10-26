package com.sakethh.plugins

import com.sakethh.api.apiRoutings
import com.sakethh.bot.botRoutings
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        apiRoutings()
        botRoutings()
    }
}