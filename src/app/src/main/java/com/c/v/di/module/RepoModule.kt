package com.c.v.di.module

import com.c.v.data.UserGroupsRepositoryImpl
import com.c.v.data.db.user_groups.VKUserGroupDao
import com.c.v.domain.userGroups.UserGroupsRepository
import com.c.v.mapper.user_group.UserGroupApiToEntityMapper
import com.c.v.mapper.user_group.UserGroupEntityToDtoMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideMarketsRepo(
        dao: VKUserGroupDao,
        apiToEntityMapper: UserGroupApiToEntityMapper,
        entityToDtoMapper: UserGroupEntityToDtoMapper
    ): UserGroupsRepository {
        return UserGroupsRepositoryImpl(dao, apiToEntityMapper, entityToDtoMapper)
    }
}