package com.delacrixmorgan.twilight.android.data.model

import com.delacrixmorgan.twilight.android.data.controller.ZoneDataController
import java.util.*

data class Location(
    val uuid: String = UUID.randomUUID().toString(),
    val zoneUuid: String,
    val name: String,
    val personName: String? = null
) {
    val zone: Zone?
        get() {
            return ZoneDataController.getZoneById(zoneUuid)
        }
}