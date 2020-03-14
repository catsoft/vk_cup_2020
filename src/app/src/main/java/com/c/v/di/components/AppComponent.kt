package com.c.v.di.components

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton
import android.app.Application
import com.c.v.CustomApplication
import com.c.v.di.module.*
import com.c.v.mapper.user_group.UserGroupsMapperModule
import com.c.v.ui.markets_flow.market_list.MarketListFragment
import dagger.BindsInstance

@Singleton
@Component(modules = [
    DaoModule::class,
    ApiModule::class,
    FragmentBuilderModule::class,
    AndroidInjectionModule::class,
    RepoModule::class,
    UserGroupsMapperModule::class,
    MainActivityModule::class,
    ViewModelModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app : CustomApplication)

    fun inject(fragment: MarketListFragment)


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
