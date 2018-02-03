package com.atanana.ratinghack

import java.net.URL

const val SITE_ADDRESS = "http://rating.chgk.info/"

object Connector {
    fun tournamentTeamsPage(tournamentId: Int): String {
        val url = "${SITE_ADDRESS}api/tournaments/$tournamentId/list.json"
        return URL(url).readText()
    }
}