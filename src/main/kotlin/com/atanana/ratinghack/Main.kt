package com.atanana.ratinghack

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)

    routing {
        get("/tournament/{n}") {
            val tournamentId = call.parameters["n"]!!.toInt()
            val info = TeamsOnTournamentInfoProvider.getInfo(tournamentId)
            call.respondText(info, ContentType.Application.Json)
        }

        get("/") {
            val json = InterestingTournamentsProvider.getInterestingTournamentsJson()
            val page = javaClass.getResource("/web/index.html").readText()
            val resultPage = page.replaceFirst("%tournaments_data%", json.toJsonString())
            call.respondText(resultPage, ContentType.Text.Html)
        }

        static {
            resources("web")
        }
    }
}