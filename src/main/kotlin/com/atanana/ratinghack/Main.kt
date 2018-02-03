package com.atanana.ratinghack

import com.beust.klaxon.Klaxon
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.experimental.async

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/") {
            call.respondText("My Example Blog2", ContentType.Text.Html)
        }

        get("/tournament/{n}") {
            val tournamentId = call.parameters["n"]!!.toInt()
            val data = Connector.tournamentTeamsData(tournamentId)
            val teams = Klaxon().parseArray<RawTournamentTeam>(data) ?: emptyList()
            val res = teams.map { team ->
                async {
                    Connector.teamsResultsData(team.teamId.toInt())
                }
            }.map { it.await() }
            call.respondText(res.toString(), ContentType.Text.Html)
        }
    }
}