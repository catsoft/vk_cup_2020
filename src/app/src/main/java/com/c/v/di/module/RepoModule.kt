package com.c.v.di.module

import com.c.v.data.GroupsRepositoryImpl
import com.c.v.data.db.VKGroupDao
import com.c.v.domain.markets.GroupsRepository
import dagger.Module
import dagger.Provides
import org.modelmapper.ModelMapper
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideMarketsRepo(dao: VKGroupDao, modelMapper: ModelMapper): GroupsRepository {
        return GroupsRepositoryImpl(dao, modelMapper)
    }
}