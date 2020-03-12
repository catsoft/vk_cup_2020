package com.c.v.di.module

import com.c.v.data.db.models.groups.VKGroupEntity
import com.c.v.data.network.vkApi.model.VKGroupApi
import dagger.Module
import dagger.Provides
import org.modelmapper.ModelMapper
import org.modelmapper.PropertyMap
import org.modelmapper.convention.MatchingStrategies
import javax.inject.Singleton

@Module
class MapperModule {

    @Singleton
    @Provides
    fun provideMapper(): ModelMapper {
        val mapper = ModelMapper()

        val groupEntityToApiPropertyMap: PropertyMap<VKGroupEntity, VKGroupApi> = object : PropertyMap<VKGroupEntity, VKGroupApi>() {
            override fun configure() {
            }
        }

        mapper.addMappings(groupEntityToApiPropertyMap)

        mapper.configuration.matchingStrategy = MatchingStrategies.LOOSE;

        return mapper
    }
}