package com.c.v.mapper.user_group

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserGroupsMapperModule() {

    @Singleton
    @Provides
    fun provideUserGroupApiToEntityMapper(): UserGroupApiToEntityMapper {
        return UserGroupApiToEntityMapper()
    }

    @Singleton
    @Provides
    fun provideUserGroupEntityToDtoMapper(): UserGroupEntityToDtoMapper {
        return UserGroupEntityToDtoMapper()
    }

    @Singleton
    @Provides
    fun provideUserGroupDtoToItemPresentationMapper(): UserGroupDtoToItemPresentationMapper {
        return UserGroupDtoToItemPresentationMapper()
    }

    @Singleton
    @Provides
    fun provideUserGroupDtoToDetailPresentationMapper(): UserGroupDtoToDetailPresentationMapper {
        return UserGroupDtoToDetailPresentationMapper()
    }
}

