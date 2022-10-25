package com.sakethh

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.sakethh.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSecurity()
        configureRouting()
    }.start(wait = true)
}
