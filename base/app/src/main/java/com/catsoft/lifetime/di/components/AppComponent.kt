package com.catsoft.lifetime.di.components

import com.catsoft.lifetime.presentation.base.BaseViewModel
import com.catsoft.lifetime.presentation.timeRecordsList.TimeRecordsListViewModel
import com.catsoft.lifetime.ui.base.TimeApplication
import com.catsoft.lifetime.ui.screens.root.RootActivity
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import android.app.Application
import com.catsoft.lifetime.di.module.*
import com.catsoft.lifetime.ui.screens.timeRecordsList.TimeRecordsListFragment
import dagger.BindsInstance



@Singleton
@Component(modules = [AppModule::class,
    DaoModule::class,
    AndroidInjectionModule::class,
    RootActivityModule::class,
    ViewModelModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app : TimeApplication)

    fun inject(timeRecordsFragment: TimeRecordsListFragment)

    fun inject(timeRecordsListViewModel: TimeRecordsListViewModel)


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
