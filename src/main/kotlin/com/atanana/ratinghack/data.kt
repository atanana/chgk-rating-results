package com.atanana.ratinghack

import com.beust.klaxon.Json

data class RawTournamentTeam(
        @Json(name = "idteam")
        val teamId: String
)