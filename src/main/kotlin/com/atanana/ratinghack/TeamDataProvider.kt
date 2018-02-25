package com.atanana.ratinghack

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader

object TeamDataProvider {
    private val parser = Parser()

    private val teamInfoCache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .build(CacheLoader.from<Int, TeamInfo?> { teamId ->
                val data = Connector.teamInfo(teamId!!)
                ((parser.parse(StringBuilder(data)) as JsonArray<*>).firstOrNull() as JsonObject).toTeamInfo()
            })

    fun getTeamData(teamId: Int, tournamentId: Int): TeamData? {
        val teamInfo = teamInfoCache.get(teamId)
        val teamResults = getTeamResult(teamId, tournamentId)
        return if (teamInfo != null && teamResults != null) {
            TeamData(teamInfo, teamResults)
        } else {
            null
        }
    }

    private fun getTeamResult(teamId: Int, tournamentId: Int): TeamResult? {
        val data = Connector.teamsResultsData(teamId)
        return TeamResultsExtractor.getTeamResult(data, tournamentId)
    }
}