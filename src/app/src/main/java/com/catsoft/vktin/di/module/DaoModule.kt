package com.catsoft.vktin.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {

//    @Singleton
//    @Provides
//    fun provideTimeDatabase(app: Application): TimeDatabase {
//        return Room.databaseBuilder(app, TimeDatabase::class.java, "time_records.db")
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun provideTimeRecordsDao(timeDatabase: TimeDatabase): TimeRecordDao {
//        return timeDatabase.timeRecordDao()
//    }
}