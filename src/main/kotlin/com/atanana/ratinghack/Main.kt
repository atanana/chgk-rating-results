package com.atanana.ratinghack

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/") {
            call.respondText("My Example Blog2", ContentType.Text.Html)
        }

        get("/tournament/{n}") {
            val tournamentId = call.parameters["n"]!!.toInt()
            call.respondText("Tournament $tournamentId", ContentType.Text.Html)
        }
    }
}