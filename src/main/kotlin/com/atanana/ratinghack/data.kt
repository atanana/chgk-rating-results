package com.atanana.ratinghack

import com.beust.klaxon.Json
import com.beust.klaxon.json
import java.time.LocalDateTime

data class RawTournamentTeam(
        @Json(name = "idteam")
        val teamId: String
)

data class TeamInfo(val id: Int, val name: String, val city: String)

data class TeamResult(
        val rating: Int,
        val predictedPlace: Float,
        val place: Float,
        val bonus: Int,
        val realBonus: Int,
        val points: Int
)

data class TournamentData(
        val id: Int,
        val name: String,
        val start: LocalDateTime,
        val end: LocalDateTime
)

data class TeamData(val teamInfo: TeamInfo, val teamResult: TeamResult) {
    fun toJson() =
            json {
                obj(
                        "id" to teamInfo.id,
                        "name" to teamInfo.name,
                        "city" to teamInfo.city,
                        "rating" to teamResult.rating,
                        "predictedPlace" to teamResult.predictedPlace,
                        "place" to teamResult.place,
                        "bonus" to teamResult.bonus,
                        "realBonus" to teamResult.realBonus,
                        "points" to teamResult.points
                )
            }
}