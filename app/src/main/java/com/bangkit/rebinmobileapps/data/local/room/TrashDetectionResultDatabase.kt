package com.bangkit.rebinmobileapps.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.rebinmobileapps.data.local.entity.DetectionResultEntity
import com.bangkit.rebinmobileapps.data.local.entity.History

@Database(entities = [DetectionResultEntity::class], version = 1, exportSchema = false)
abstract class TrashDetectionResultDatabase : RoomDatabase() {

    abstract fun detectionResultDao() : DetectionResultDao

    companion object {
        @Volatile
        private var INSTANCE: TrashDetectionResultDatabase? = null

        fun getDatabase(context: Context): TrashDetectionResultDatabase =
            INSTANCE ?: synchronized(TrashDetectionResultDatabase::class.java) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TrashDetectionResultDatabase::class.java, "trash_detection_database"
                ).build()
            }
    }
}