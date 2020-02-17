package com.catsoft.lifetime.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.catsoft.lifetime.dal.TimeRecordDao
import com.catsoft.lifetime.domain.TimeRecordModel

@Database(entities = [TimeRecordModel::class], version = 1, exportSchema = false)
abstract class TimeDatabase : RoomDatabase() {

    abstract fun timeRecordDao(): TimeRecordDao

    companion object {

        fun create(context: Context): TimeDatabase {
            return Room.databaseBuilder(context, TimeDatabase::class.java, "time-db").build()
        }
    }

}

