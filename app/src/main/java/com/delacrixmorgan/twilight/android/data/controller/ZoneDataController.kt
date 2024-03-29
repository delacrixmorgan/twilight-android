package com.delacrixmorgan.twilight.android.data.controller

import com.delacrixmorgan.twilight.android.data.model.Zone
import com.delacrixmorgan.twilight.android.extractKeywords
import org.threeten.bp.ZoneId

object ZoneDataController {
    private var zones = mutableListOf<Zone>()

    init {
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

        zones = filteredZones.map {
            val keywords = it.extractKeywords()
            Zone(
                timeZoneId = it,
                name = keywords[keywords.size - 1],
                keywords = keywords
            )
        }.toMutableList()
    }

    fun getZoneById(timeZoneId: String?): Zone? {
        return zones.firstOrNull { it.timeZoneId == timeZoneId }
    }

    fun getZone(searchQuery: String? = null, searchQueries: List<String>? = null): List<Zone> {
        var filteredList = zones

        searchQuery?.let {
            filteredList = filteredList.filter {
                it.keywords.any { keyword ->
                    keyword.contains(searchQuery, ignoreCase = true)
                }
            }.toMutableList()
        }

        searchQueries?.forEach { query ->
            filteredList = filteredList.filter {
                it.keywords.any { keyword ->
                    keyword.contains(query, ignoreCase = true)
                }
            }.toMutableList()
        }

        return filteredList.sortedBy { it.name }
    }
}