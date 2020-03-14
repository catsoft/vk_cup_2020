package com.c.v.data.db.user_groups

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VKUserGroupEntity::class], version = 1, exportSchema = false)
abstract class VKUserGroupsDatabase : RoomDatabase() {

    abstract fun vkGroupDao(): VKUserGroupDao

    companion object {

        fun create(context: Context): VKUserGroupsDatabase {
            return Room.databaseBuilder(context, VKUserGroupsDatabase::class.java, "vk-db").fallbackToDestructiveMigration().build()
        }
    }

}

