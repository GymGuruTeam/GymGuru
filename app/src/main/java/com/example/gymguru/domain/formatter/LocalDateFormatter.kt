package com.example.gymguru.domain.formatter

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class LocalDateFormatter @Inject constructor() {
    operator fun invoke(localDate: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)
        return localDate.format(formatter)
    }

    operator fun invoke(date: String?): LocalDate? {
        if (date.isNullOrEmpty()) return null
        val formatter = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)
        return LocalDate.parse(date, formatter)
    }

    companion object {
        private const val DEFAULT_TIME_FORMAT = "dd-MM-yyyy"
    }
}
