package com.c.v.di.module

import android.app.Application
import com.c.v.data.db.VKDatabase
import com.c.v.data.db.VKGroupDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {

    @Singleton
    @Provides
    fun provideTimeDatabase(app: Application): VKDatabase {
        return VKDatabase.create(app)
    }

    @Singleton
    @Provides
    fun provideTimeRecordsDao(timeDatabase: VKDatabase): VKGroupDao {
        return timeDatabase.vkGroupDao()
    }
}

