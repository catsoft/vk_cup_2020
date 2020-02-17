package com.catsoft.lifetime.di.module


import com.catsoft.lifetime.ui.screens.goals.GoalsFragment
import com.catsoft.lifetime.ui.screens.graphs.GraphsFragment
import com.catsoft.lifetime.ui.screens.settings.SettingsFragment
import com.catsoft.lifetime.ui.screens.timeRecordsList.TimeRecordsListFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeGoalFragment(): GoalsFragment

    @ContributesAndroidInjector
    abstract fun contributeGraphFragment(): GraphsFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun contributeTimeRecordsFragment(): TimeRecordsListFragment
}