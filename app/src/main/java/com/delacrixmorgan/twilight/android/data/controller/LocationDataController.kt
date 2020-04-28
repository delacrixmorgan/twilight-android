package com.delacrixmorgan.twilight.android.data.controller

import com.delacrixmorgan.twilight.android.data.model.Location

object LocationDataController {
    var locations = mutableListOf<Location>()

    fun getLocationById(uuid: String): Location? {
        return locations.firstOrNull { it.uuid == uuid }
    }

    fun getLocation(): List<Location> {
        return locations
    }
}