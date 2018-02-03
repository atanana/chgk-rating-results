package com.atanana.ratinghack

import com.beust.klaxon.JsonArray
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.experimental.async

object TeamsOnTournamentInfoProvider {
    private val klaxon = Klaxon()

    suspend fun getInfo(tournamentId: Int): String {
        val teamsJson = getTeamIds(tournamentId)
                .map { team ->
                    async {
                        TeamDataProvider.getTeamData(team.teamId.toInt(), tournamentId)
                    }
                }
                .mapNotNull { it.await() }
                .sortedWith(compareBy({ it.teamResult.place }, { it.teamInfo.name }))
                .map { it.toJson() }

        return JsonArray(teamsJson).toJsonString()
    }

    private fun getTeamIds(tournamentId: Int): List<RawTournamentTeam> {
        val data = Connector.tournamentTeamsData(tournamentId)
        return klaxon.parseArray(data) ?: emptyList()
    }
}