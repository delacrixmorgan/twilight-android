package com.delacrixmorgan.twilight.android.data.timezone

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class Timezone(
    private val zoneIdString: String,
    val region: Region,
    val country: List<String> = listOf(),
    val states: List<String> = listOf(),
    val cities: List<String> = listOf()
) {
    val zoneId: ZoneId get() = ZoneId.of(zoneIdString)
    val zone get() = zoneIdString.split("/").first()
    val city get() = zoneIdString.split("/").last().replace(Regex("[_-]"), " ")
    val keywords: List<String> get() = country + states + cities
}

fun Timezone.localTime(
    pattern: String = "h:mm a"
): String = LocalDateTime.now()
    .atZone(ZoneId.systemDefault())
    .withZoneSameInstant(zoneId)
    .format(DateTimeFormatter.ofPattern(pattern))