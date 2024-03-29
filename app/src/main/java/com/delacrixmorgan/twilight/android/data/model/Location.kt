package com.delacrixmorgan.twilight.android.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.delacrixmorgan.twilight.android.data.controller.ZoneDataController
import com.delacrixmorgan.twilight.android.toZonedDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.util.*

@Entity(tableName = "Location")
data class Location(
    @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "timeZoneId") var timeZoneId: String? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "description") var description: String? = null
) {
    val zone: Zone?
        get() {
            return ZoneDataController.getZoneById(timeZoneId)
        }

    fun getCurrentZonedDateTime(date: Date): ZonedDateTime {
        val zoneId = ZoneId.of(timeZoneId)
        return ZonedDateTime.ofInstant(date.toZonedDateTime().toInstant(), zoneId)
    }
}