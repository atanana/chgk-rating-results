package com.atanana.ratinghack

import com.beust.klaxon.JsonArray
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

object TeamsOnTournamentInfoProvider {
    private val klaxon = Klaxon()

    suspend fun getInfo(tournamentId: Int): String = coroutineScope {
        val teamsJson = getTeamIds(tournamentId)
                .map { team ->
                    async(Dispatchers.IO) {
                        TeamDataProvider.getTeamData(team.teamId.toInt(), tournamentId)
                    }
                }
                .mapNotNull { it.await() }
                .sortedWith(compareBy({ it.teamResult.place }, { it.teamInfo.name }))
                .map { it.toJson() }

        JsonArray(teamsJson).toJsonString()
    }

    private fun getTeamIds(tournamentId: Int): List<RawTournamentTeam> {
        val data = Connector.tournamentTeamsData(tournamentId)
        return klaxon.parseArray(data) ?: emptyList()
    }
}