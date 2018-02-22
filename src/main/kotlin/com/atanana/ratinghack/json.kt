package com.atanana.ratinghack

import com.beust.klaxon.JsonObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

fun JsonObject.toTournamentData(): TournamentData =
        TournamentData(
                string("idtournament")!!.toInt(),
                string("name")!!,
                LocalDateTime.parse(string("date_start"), formatter),
                LocalDateTime.parse(string("date_end"), formatter),
                string("type_name")!!
        )

fun JsonObject.toTeamInfo(): TeamInfo =
        TeamInfo(
                string("idteam")!!.toInt(),
                string("name")!!,
                string("town")!!
        )