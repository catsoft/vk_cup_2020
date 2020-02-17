package com.catsoft.lifetime.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.catsoft.lifetime.di.CustomViewModelFactory
import com.catsoft.lifetime.di.ViewModelKey
import com.catsoft.lifetime.presentation.goals.GoalsViewModel
import com.catsoft.lifetime.presentation.graphs.GraphsViewModel
import com.catsoft.lifetime.presentation.settings.SettingsViewModel
import com.catsoft.lifetime.presentation.timeRecordsList.TimeRecordsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TimeRecordsListViewModel::class)
    abstract fun bindTimeRecordViewModel(userViewModel: TimeRecordsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GraphsViewModel::class)
    abstract fun bindGraphsViewModel(searchViewModel: GraphsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GoalsViewModel::class)
    abstract fun bindGoalsViewModel(repoViewModel: GoalsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(repoViewModel: SettingsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: CustomViewModelFactory): ViewModelProvider.Factory
}