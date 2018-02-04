package com.atanana.ratinghack

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.content.defaultResource
import io.ktor.content.resources
import io.ktor.content.static
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/tournament/{n}") {
            val tournamentId = call.parameters["n"]!!.toInt()
            val info = TeamsOnTournamentInfoProvider.getInfo(tournamentId)
            call.respondText(info, ContentType.Application.Json)
        }

        static {
            defaultResource("index.html", "web")
            resources("web")
        }
    }
}