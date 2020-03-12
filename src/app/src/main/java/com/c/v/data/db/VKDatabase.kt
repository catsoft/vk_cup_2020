package com.c.v.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.c.v.data.db.models.groups.VKGroupEntity

@Database(entities = [VKGroupEntity::class], version = 1, exportSchema = false)
abstract class VKDatabase : RoomDatabase() {

    abstract fun vkGroupDao(): VKGroupDao

    companion object {

        fun create(context: Context): VKDatabase {
            return Room.databaseBuilder(context, VKDatabase::class.java, "vk-db").fallbackToDestructiveMigration().build()
        }
    }

}

