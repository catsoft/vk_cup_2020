package com.catsoft.vktin.di.components

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton
import android.app.Application
import com.catsoft.vktin.CustomApplication
import com.catsoft.vktin.di.module.AppModule
import com.catsoft.vktin.di.module.DaoModule
import com.catsoft.vktin.di.module.RootActivityModule
import com.catsoft.vktin.di.module.ViewModelModule
import dagger.BindsInstance


@Singleton
@Component(modules = [AppModule::class,
    DaoModule::class,
    AndroidInjectionModule::class,
    RootActivityModule::class,
    ViewModelModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app : CustomApplication)

//    fun inject(timeRecordsFragment: TimeRecordsListFragment)


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
