package com.atanana.ratinghack

import org.apache.commons.csv.CSVFormat
import org.slf4j.LoggerFactory
import java.io.StringReader

object TeamResultsExtractor {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val parser = CSVFormat.RFC4180.withDelimiter(';')

    private fun String.toPlace(): Float = replace(',', '.').toFloat()

    fun getTeamResult(data: String, tournamentId: Int): TeamResult? {
        try {
            val tournamentIdStr = tournamentId.toString()
            val row = parser.parse(StringReader(data)).records
                    .firstOrNull { it.get(0) == tournamentIdStr }
            return row?.let {
                TeamResult(
                        rating = row[6].toInt(),
                        predictedPlace = row[7].toPlace(),
                        place = row[8].toPlace(),
                        bonus = row[10].toInt(),
                        realBonus = row[11].toInt(),
                        points = row[12].toInt()
                )
            }
        } catch (e: Exception) {
            logger.error("Cannot process data $data!", e)
            return null
        }
    }
}