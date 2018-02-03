package com.atanana.ratinghack

import com.beust.klaxon.Klaxon
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/") {
            call.respondText("My Example Blog2", ContentType.Text.Html)
        }

        get("/tournament/{n}") {
            val tournamentId = call.parameters["n"]!!.toInt()
            val data = Connector.tournamentTeamsPage(tournamentId)
            val teams = Klaxon().parseArray<RawTournamentTeam>(data)
            call.respondText(teams.toString(), ContentType.Text.Html)
        }
    }
}