package pl.kapucyni.gory4.app.common.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char

fun LocalDateTime.getFormattedDateTime() = format(
    LocalDateTime.Format {
        time(
            LocalTime.Format {
                hour()
                char(':')
                minute()
            }
        )
        chars(", ")
        date(
            LocalDate.Format {
                dayOfMonth()
                char('.')
                monthNumber()
                char('.')
                year()
            }
        )
    }
)