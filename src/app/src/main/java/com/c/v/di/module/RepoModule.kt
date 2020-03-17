package com.c.v.di.module

import com.c.v.data.UserGroupsRepositoryImpl
import com.c.v.data.WallRepositoryImpl
import com.c.v.data.db.user_groups.VKUserGroupDao
import com.c.v.domain.userGroups.UserGroupsRepository
import com.c.v.domain.wall.WallRepository
import com.c.v.mapper.user_group.UserGroupApiToEntityMapper
import com.c.v.mapper.user_group.UserGroupEntityToDtoMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideUserGroupsRepo(
        dao: VKUserGroupDao,
        apiToEntityMapper: UserGroupApiToEntityMapper,
        entityToDtoMapper: UserGroupEntityToDtoMapper
    ): UserGroupsRepository = UserGroupsRepositoryImpl(dao, apiToEntityMapper, entityToDtoMapper)

    @Singleton
    @Provides
    fun provideWallRepo(): WallRepository = WallRepositoryImpl()
}