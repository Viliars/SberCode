package com.devian.sbercode.mobile.extensions

import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatterBuilder
import org.threeten.bp.temporal.ChronoField
import java.util.*

enum class DateFormat(val formatter: DateTimeFormatter) {
    LLLL_YYYY(DateTimeFormatter.ofPattern("LLLL yyyy", Locale("ru", "RU"))),
    DD_MM_YYYY(
        DateTimeFormatterBuilder()
            .appendPattern("dd/MM/yyyy")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0L)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0L)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0L)
            .toFormatter(Locale("ru", "RU"))
    ),
    DD_MM_YYYY_dots(
        DateTimeFormatterBuilder()
            .appendPattern("dd.MM.yyyy")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0L)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0L)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0L)
            .toFormatter(Locale("ru", "RU"))
    ),
    HH_MM(DateTimeFormatter.ofPattern("HH:mm", Locale("ru", "RU"))),
    HH_MM_SS(DateTimeFormatter.ofPattern("HH:mm:ss", Locale("ru", "RU"))),
    DD_MMMM_HH_MM(DateTimeFormatter.ofPattern("dd MMMM HH:mm", Locale("ru", "RU"))),
    DD_MMMM_YYYY(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru", "RU"))),
    DD_MMMM(DateTimeFormatter.ofPattern("dd MMMM", Locale("ru", "RU"))),
    D_MMMM(DateTimeFormatter.ofPattern("d MMMM", Locale("ru", "RU"))),
    DD_MMMM_YYYY_HH_MM(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", Locale("ru", "RU"))),
    SQLITE(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale("ru", "RU"))),
    LOG(DateTimeFormatter.ofPattern("HH:mm:ss O dd.MM.yyyy", Locale("ru", "RU"))),
    API_ISO_8601(
        DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .optionalStart()
            .appendOffsetId()
            .toFormatter(Locale("ru", "RU"))
    ),
    D_MMMM_EEEE(DateTimeFormatter.ofPattern("d MMMM, EEEE", Locale("ru", "RU")))
}

fun String.toDate(format: DateFormat): ZonedDateTime {
    return try {
        ZonedDateTime.from(format.formatter.parse(this))
    } catch (e: DateTimeException) {
        LocalDateTime.parse(this, format.formatter).atZone(ZoneId.systemDefault())
    }
}

fun String.toTime(format: DateFormat): LocalTime {
    return LocalTime.from(format.formatter.parse(this))
}

fun LocalTime.format(format: DateFormat): String {
    return format.formatter.format(this)
}

fun ZonedDateTime.format(format: DateFormat): String {
    return format.formatter.format(this)
}

fun ZonedDateTime.isSameDay(date: ZonedDateTime): Boolean {
    return this.toLocalDate() == date.toLocalDate()
}

fun Date.toZonedDateTime(zoneId: ZoneId = ZoneId.systemDefault()): ZonedDateTime {
    val instant = DateTimeUtils.toInstant(this)
    return ZonedDateTime.ofInstant(instant, zoneId)
}