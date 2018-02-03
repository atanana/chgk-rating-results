package com.atanana.ratinghack

import com.beust.klaxon.Json
import com.beust.klaxon.json

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

data class TeamResult(
        val rating: Int,
        val predictedPlace: Float,
        val place: Float,
        val bonus: Int,
        val realBonus: Int,
        val points: Int
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