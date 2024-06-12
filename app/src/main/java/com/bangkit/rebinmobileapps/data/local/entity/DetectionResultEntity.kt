package com.bangkit.rebinmobileapps.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detection_result")
data class DetectionResultEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "waste_type")
    var wasteType: String = "",

    @ColumnInfo(name = "accuracy")
    var accuracy: String = "",

    @ColumnInfo(name = "image_url")
    var imageUrl: String? = "",

    @ColumnInfo(name = "created_at")
    var createdAt: String = ""
)
