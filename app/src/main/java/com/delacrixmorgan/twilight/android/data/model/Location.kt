package com.delacrixmorgan.twilight.android.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.delacrixmorgan.twilight.android.data.controller.ZoneDataController
import java.util.*

@Entity(tableName = "Location")
data class Location(
    @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "timeZoneId") val timeZoneId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "personName") val personName: String? = null
) {
    val zone: Zone?
        get() {
            return ZoneDataController.getZoneById(timeZoneId)
        }
}