package com.c.v.di.module

import com.c.v.data.network.vkApi.IVkApi
import com.c.v.data.network.vkApi.VkApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideVKApi(): IVkApi {
        return VkApi()
    }
}