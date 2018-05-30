package com.atanana.ratinghack

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import java.time.LocalDateTime

object InterestingTournamentsProvider {
    fun getInterestingTournamentsJson(): JsonArray<JsonObject> {
        val now = LocalDateTime.now()
        val tournaments = TournamentsDataProvider.getTournaments()
                .filter { it.start.isBefore(now) && it.end.plusHours(4).isAfter(now) }

        return JsonArray(tournaments.map { tournamentToJson(it) })
    }

    private fun tournamentToJson(data: TournamentData): JsonObject =
            JsonObject(mapOf(
                    "id" to data.id,
                    "name" to data.name
            ))
}