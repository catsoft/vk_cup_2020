package com.c.v.di.module

import com.c.v.data.db.models.groups.VKGroupEntity
import com.c.v.data.dto.VKGroupDto
import com.c.v.data.network.vkApi.model.VKGroupApi
import com.c.v.ui.unsubscribe_flow.group_list.VKGroupPresentation
import dagger.Module
import dagger.Provides
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import javax.inject.Singleton

@Module
class MapperModule {

    @Singleton
    @Provides
    fun provideMapper(): ModelMapper {
        val mapper = ModelMapper()

        mapper.typeMap(VKGroupApi::class.java, VKGroupEntity::class.java)
        mapper.typeMap(VKGroupEntity::class.java, VKGroupDto::class.java)
        mapper.typeMap(VKGroupDto::class.java, VKGroupPresentation::class.java)

        mapper.configuration.matchingStrategy = MatchingStrategies.LOOSE

        return mapper
    }
}