package com.atanana.ratinghack

import com.beust.klaxon.Klaxon

object TeamDataProvider {
    private val klaxon = Klaxon()

    fun getTeamData(teamId: Int, tournametId: Int): TeamData? {
        val teamInfo = getTeamInfo(teamId)
        val teamResults = getTeamResults(teamId, tournametId)
        return if (teamInfo != null && teamResults != null) {
            TeamData(teamInfo, teamResults)
        } else {
            null
        }
    }

    private fun getTeamInfo(teamId: Int): TeamInfo? {
        val data = Connector.teamInfo(teamId)
        val rawTeamInfo = klaxon.parseArray<RawTeamInfo>(data)?.firstOrNull()
        return rawTeamInfo?.toInfo()
    }

    private fun getTeamResults(teamId: Int, tournametId: Int): TeamResult? {
        val data = Connector.teamsResultsData(teamId)
        return TeamResultsExtractor.getTeamResult(data, tournametId)
    }
}