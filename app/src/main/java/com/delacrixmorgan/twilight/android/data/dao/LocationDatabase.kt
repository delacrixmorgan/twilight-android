package com.delacrixmorgan.twilight.android.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.delacrixmorgan.twilight.android.data.model.Location

@Database(entities = [(Location::class)], version = 1, exportSchema = false)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDataDao(): LocationDataDao

    companion object {
        private var INSTANCE: LocationDatabase? = null

        fun getInstance(context: Context): LocationDatabase? {
            if (INSTANCE == null) {
                synchronized(LocationDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LocationDatabase::class.java,
                        "location.db"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}