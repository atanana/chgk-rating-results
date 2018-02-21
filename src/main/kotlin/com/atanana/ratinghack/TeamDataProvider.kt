package com.atanana.ratinghack

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import java.util.concurrent.TimeUnit

object TeamDataProvider {
    private val parser = Parser()

    private val teamInfoCache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .build(CacheLoader.from<Int, TeamInfo?> { teamId ->
                val data = Connector.teamInfo(teamId!!)
                ((parser.parse(StringBuilder(data)) as JsonArray<*>).firstOrNull() as JsonObject).toTeamInfo()
            })

    private val teamResultsCache = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.DAYS)
            .build(CacheLoader.from<Pair<Int, Int>, TeamResult?> {
                val (teamId, tournamentId) = it!!
                val data = Connector.teamsResultsData(teamId)
                TeamResultsExtractor.getTeamResult(data, tournamentId)
            })


    fun getTeamData(teamId: Int, tournamentId: Int): TeamData? {
        val teamInfo = teamInfoCache.get(teamId)
        val teamResults = teamResultsCache.get(Pair(teamId, tournamentId))
        return if (teamInfo != null && teamResults != null) {
            TeamData(teamInfo, teamResults)
        } else {
            null
        }
    }
}