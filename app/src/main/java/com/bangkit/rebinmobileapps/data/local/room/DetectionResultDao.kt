package com.bangkit.rebinmobileapps.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.rebinmobileapps.data.local.entity.DetectionResultEntity

@Dao
interface DetectionResultDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDetectionResult(detectionResult: DetectionResultEntity)

    @Query("SELECT * FROM detection_result ORDER BY created_at DESC")
    fun getAllDetectionResults(): LiveData<List<DetectionResultEntity>>

    @Delete
    fun deleteDetectionResults(detectionResultEntity: DetectionResultEntity)

    @Query("DELETE from detection_result")
    fun deleteAllDetectionResults()

}