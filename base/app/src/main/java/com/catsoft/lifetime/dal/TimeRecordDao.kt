package com.catsoft.lifetime.dal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.catsoft.lifetime.domain.TimeRecordModel

@Dao
interface TimeRecordDao {

    @Insert
    fun insert(records: List<TimeRecordModel>): List<Long>

    @Insert
    fun insert(record: TimeRecordModel): Long

    @Query("SELECT * FROM time_record")
    fun getTimeRecords(): LiveData<List<TimeRecordModel>>
}