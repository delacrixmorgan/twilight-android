package com.delacrixmorgan.twilight.android.data.dao

import androidx.room.*
import com.delacrixmorgan.twilight.android.data.model.Location

@Dao
interface LocationDataDao {
    @Query("SELECT * from Location")
    suspend fun getLocations(): List<Location>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateLocation(location: Location)

    @Query("DELETE from Location")
    suspend fun deleteLocations()

    @Query("DELETE FROM Location WHERE uuid = :uuid")
    suspend fun deleteLocationById(uuid: String)
}