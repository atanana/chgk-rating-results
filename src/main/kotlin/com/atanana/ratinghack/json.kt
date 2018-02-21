package com.atanana.ratinghack

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDatetimeHolder : Converter<LocalDateTime> {
    companion object {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }

    override fun fromJson(jv: JsonValue): LocalDateTime = LocalDateTime.parse(jv.string!!, formatter)

    override fun toJson(value: LocalDateTime): String? = value.format(formatter)
}

@Target(AnnotationTarget.FIELD)
annotation class TournamentDate