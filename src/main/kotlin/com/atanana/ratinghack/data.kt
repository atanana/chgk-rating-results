package com.atanana.ratinghack

import com.beust.klaxon.Json

data class RawTournamentTeam(
        @Json(name = "idteam")
        val teamId: String
)

data class RawTeamInfo(
        @Json(name = "idteam")
        val id: String,
        val name: String,
        @Json(name = "town")
        val city: String
) {
    fun toInfo() = TeamInfo(id.toInt(), name, city)
}

data class TeamInfo(val id: Int, val name: String, val city: String)