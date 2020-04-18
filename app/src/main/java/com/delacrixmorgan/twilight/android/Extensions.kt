package com.delacrixmorgan.twilight.android

import org.threeten.bp.DateTimeUtils
import org.threeten.bp.Instant
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*

fun Date.toZonedDateTime(): ZonedDateTime {
    val calendar = Calendar.getInstance().apply { time = this@toZonedDateTime }
    return DateTimeUtils.toZonedDateTime(calendar)
}

fun Date.toStringFormat(): String {
    val calendar = Calendar.getInstance().apply { time = this@toStringFormat }
    val zonedDateTime = DateTimeUtils.toZonedDateTime(calendar)

    return DateTimeFormatter.ISO_INSTANT.format(zonedDateTime)
}

fun Date.formatDate(format: String = "EEE, dd MMMM"): String {
    return SimpleDateFormat(format, Locale.US).format(this)
}

fun String.toDateFormat(): Date {
    val dateInstant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(this))
    return DateTimeUtils.toDate(dateInstant)
}

fun String.extractKeywords(): List<String> {
    val splitString = this.split("/")
    val name = splitString[1].replace("_", " ")
    val list = mutableListOf(splitString[0], name)

    name.addExceptions(list)
    return list
}

fun String.addExceptions(list: MutableList<String>) {
    val exceptionList = when (this) {
        "Kuala Lumpur" -> {
            listOf("Malaysia", "Klang", "Shah Alam", "Rawang", "Subang Jaya")
        }
        "Los Angeles" -> {
            listOf("San Francisco")
        }
        "Chicago" -> {
            listOf("Columbus", "Ohio")
        }
        "New York" -> {
            listOf("New Jersey")
        }
        else -> listOf()
    }

    list.addAll(exceptionList)
}