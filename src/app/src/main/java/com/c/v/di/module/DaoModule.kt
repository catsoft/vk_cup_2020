package com.c.v.di.module

import android.app.Application
import com.c.v.data.db.user_groups.VKUserGroupsDatabase
import com.c.v.data.db.user_groups.VKUserGroupDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {

    @Singleton
    @Provides
    fun provideUserGroupDatabase(app: Application): VKUserGroupsDatabase {
        return VKUserGroupsDatabase.create(app)
    }

    @Singleton
    @Provides
    fun provideVKUserGroupDao(vkUserGroupsDatabase: VKUserGroupsDatabase): VKUserGroupDao {
        return vkUserGroupsDatabase.vkGroupDao()
    }
}

