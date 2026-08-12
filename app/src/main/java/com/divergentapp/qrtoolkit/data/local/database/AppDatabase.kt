package com.divergentapp.qrtoolkit.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.divergentapp.qrtoolkit.data.local.dao.HistoryDao
import com.divergentapp.qrtoolkit.data.local.entity.HistoryEntity

@Database(
    entities = [
        HistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
}