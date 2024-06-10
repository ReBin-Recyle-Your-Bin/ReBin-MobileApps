package com.bangkit.rebinmobileapps.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.rebinmobileapps.data.local.entity.History

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(history: History)

    @Query("SELECT * FROM history")
    fun getAll(): LiveData<List<History>>

    @Delete
    fun delete(history: History)

    @Query("DELETE from history")
    fun deleteAll()
}