package com.globalhiddenodds.mobilenews.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.globalhiddenodds.mobilenews.datasource.database.data.Hit
import com.globalhiddenodds.mobilenews.repository.HitDao

@Database(entities = [Hit::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase: RoomDatabase() {
    abstract fun hitDao(): HitDao
}