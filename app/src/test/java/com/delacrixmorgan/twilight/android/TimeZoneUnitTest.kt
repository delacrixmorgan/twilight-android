package com.delacrixmorgan.twilight.android

import com.delacrixmorgan.twilight.android.data.model.Zone
import org.junit.Test
import java.time.ZoneId

class TimeZoneUnitTest {
    @Test
    fun convertDateToString() {
        val availableZones = ZoneId.getAvailableZoneIds()
        val filteredZones = availableZones.filter {
            it.contains("America/") || it.contains("US/")
                    || it.contains("Europe/") || it.contains("Asia/")
                    || it.contains("Australia/") || it.contains("Pacific/")
                    || it.contains("Mexico/") || it.contains("Indian/")
                    || it.contains("Canada/") || it.contains("Antarctica/")
                    || it.contains("Atlantic/") || it.contains("Brazil/")
                    || it.contains("Africa/")
        }

        val zones = mutableListOf<Zone>()

        zones.addAll(filteredZones.map {
            val keywords = it.extractKeywords()
            Zone(
                timeZoneId = it,
                name = keywords[keywords.size - 1],
                keywords = keywords
            )
        })
    }
}