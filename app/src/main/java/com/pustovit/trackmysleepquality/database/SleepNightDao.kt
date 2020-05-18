package com.pustovit.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SleepNightDao {

    @Insert
    fun insert(night: SleepNight)

    @Delete
    fun delete(night: SleepNight)

    @Update
    fun update(night: SleepNight)

    @Query("SELECT * FROM daily_sleep_quality_table WHERE nightId = :id")
    fun get(id: Long): SleepNight?

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC;")
    fun getAllNights(): LiveData<List<SleepNight>>

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTonight(): SleepNight?


    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()
}
