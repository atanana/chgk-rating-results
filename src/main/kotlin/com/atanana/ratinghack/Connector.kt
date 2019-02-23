package com.atanana.ratinghack

import java.net.URL
import java.nio.charset.Charset

const val SITE_ADDRESS = "https://rating.chgk.info/"

object Connector {
    private val RATING_CHARSET = Charset.forName("cp1251")

    fun tournamentTeamsData(tournamentId: Int): String {
        val url = "${SITE_ADDRESS}api/tournaments/$tournamentId/list.json"
        return URL(url).readText()
    }

    fun teamsResultsData(teamId: Int): String {
        val url = "${SITE_ADDRESS}teams.php?team_id=$teamId&download_data=export_tournaments"
        return URL(url).readText(RATING_CHARSET)
    }

    fun teamInfo(teamId: Int): String {
        val url = "${SITE_ADDRESS}api/teams/$teamId.json"
        return URL(url).readText()
    }

    fun tournamentsPage(page: Int): String {
        val url = "${SITE_ADDRESS}api/tournaments.json?page=$page"
        return URL(url).readText()
    }
}