package com.sakethh.plugins

import com.sakethh.api.apiRoutings
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    routing {
        apiRoutings()
    }
}