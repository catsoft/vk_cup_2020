package com.catsoft.vktin.di.module

import androidx.lifecycle.ViewModelProvider
import com.catsoft.vktin.di.CustomViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

//    @Binds
//    @IntoMap
//    @ViewModelKey(TimeRecordsListViewModel::class)
//    abstract fun bindTimeRecordViewModel(userViewModel: TimeRecordsListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: CustomViewModelFactory): ViewModelProvider.Factory
}