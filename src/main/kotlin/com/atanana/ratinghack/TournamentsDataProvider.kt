package com.atanana.ratinghack

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.time.LocalDate
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

const val START_TOURNAMENTS_PAGE = 5

object TournamentsDataProvider {
    private val lock = ReentrantLock()

    private val parser = Parser()

    private var cache = listOf<TournamentData>()

    private var cacheTimestamp: LocalDate? = null

    fun getTournaments(): List<TournamentData> {
        lock.withLock {
            return if (LocalDate.now() == cacheTimestamp) {
                cache
            } else {
                cacheTimestamp = LocalDate.now()
                getTournamentsFromServer()
            }
        }
    }

    private fun getTournamentsFromServer(): ArrayList<TournamentData> {
        val result = arrayListOf<TournamentData>()
        var lastAddedTournamentsCount: Int
        var page = START_TOURNAMENTS_PAGE
        do {
            val data = Connector.tournamentsPage(page)
            val newTournamentsData = parseData(data)
            lastAddedTournamentsCount = newTournamentsData.size
            result.addAll(newTournamentsData)
            page++
        } while (lastAddedTournamentsCount > 0)

        return result
    }

    private fun parseData(data: String): List<TournamentData> {
        return ((parser.parse(StringBuilder(data)) as JsonObject)["items"] as JsonArray<*>)
                .mapChildren { it.toTournamentData() }
                .filterNotNull()
                .toList()
    }
}