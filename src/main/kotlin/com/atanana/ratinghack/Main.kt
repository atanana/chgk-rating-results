package com.atanana.ratinghack

import com.beust.klaxon.Klaxon
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
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
            val klaxon = Klaxon()
            val teams = klaxon.parseArray<RawTournamentTeam>(data) ?: emptyList()
            val res = teams.map { team ->
                async {
                    Connector.teamsResultsData(team.teamId.toInt())
                }
            }
                    .map { it.await() }
                    .map { TeamResultsExtractor.getTeamResult(it, tournamentId) }
            call.respondText(res.toString(), ContentType.Text.Html)
        }
    }
}