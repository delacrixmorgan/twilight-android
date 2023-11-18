package com.delacrixmorgan.twilight.android

import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class TimezoneUnitTest {

//    private val availableZones get() = ZoneId.getAvailableZoneIds()
//    private val zones = availableZones.filter {
//        it.contains("America/") || it.contains("US/")
//                || it.contains("Europe/") || it.contains("Asia/")
//                || it.contains("Australia/") || it.contains("Pacific/")
//                || it.contains("Mexico/") || it.contains("Indian/")
//                || it.contains("Canada/") || it.contains("Antarctica/")
//                || it.contains("Atlantic/") || it.contains("Brazil/")
//                || it.contains("Africa/")
//    }

    private val ISO_8601 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

    @Test
    fun `Given Netherlands Time with DST When convert to Malaysia Time Then return Malaysia Time`() {
        val fromDate = LocalDateTime.parse("2024-01-01T00:00:00Z", ISO_8601)
        val toDate = fromDate
            .atZone(ZoneId.of("Europe/Amsterdam"))
            .withZoneSameInstant(ZoneId.of("Asia/Kuala_Lumpur"))

        Assert.assertEquals(
            LocalDateTime.parse("2024-01-01T07:00:00Z", ISO_8601).format(ISO_8601),
            toDate.format(ISO_8601)
        )
    }

    @Test
    fun `Given Malaysia Time When convert to Netherlands with DST Then return Netherlands Time`() {
        val fromDate = LocalDateTime.parse("2024-01-01T07:00:00Z", ISO_8601)
        val toDate = fromDate
            .atZone(ZoneId.of("Asia/Kuala_Lumpur"))
            .withZoneSameInstant(ZoneId.of("Europe/Amsterdam"))

        Assert.assertEquals(
            LocalDateTime.parse("2024-01-01T00:00:00Z", ISO_8601).format(ISO_8601),
            toDate.format(ISO_8601)
        )
    }

    @Test
    fun `Given Netherlands Time without DST When convert to Malaysia Time Then return Malaysia Time`() {
        val fromDate = LocalDateTime.parse("2024-06-01T00:00:00Z", ISO_8601)
        val toDate = fromDate
            .atZone(ZoneId.of("Europe/Amsterdam"))
            .withZoneSameInstant(ZoneId.of("Asia/Kuala_Lumpur"))

        Assert.assertEquals(
            LocalDateTime.parse("2024-06-01T06:00:00Z", ISO_8601).format(ISO_8601),
            toDate.format(ISO_8601)
        )
    }

    @Test
    fun `Given Malaysia Time When convert to Netherlands without DST Then return Netherlands Time`() {
        val fromDate = LocalDateTime.parse("2024-06-01T06:00:00Z", ISO_8601)
        val toDate = fromDate
            .atZone(ZoneId.of("Asia/Kuala_Lumpur"))
            .withZoneSameInstant(ZoneId.of("Europe/Amsterdam"))

        Assert.assertEquals(
            LocalDateTime.parse("2024-06-01T00:00:00Z", ISO_8601).format(ISO_8601),
            toDate.format(ISO_8601)
        )
    }
}