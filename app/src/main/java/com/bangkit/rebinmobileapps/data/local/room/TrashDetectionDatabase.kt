package com.bangkit.rebinmobileapps.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.rebinmobileapps.data.local.entity.History

@Database(entities = [History::class], version = 1, exportSchema = false)
abstract class TrashDetectionDatabase : RoomDatabase() {

    abstract fun historyDao() : HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: TrashDetectionDatabase? = null

        fun getDatabase(context: Context): TrashDetectionDatabase =
            INSTANCE ?: synchronized(TrashDetectionDatabase::class.java) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TrashDetectionDatabase::class.java, "trash_detection_database"
                ).build()
            }
    }
}












