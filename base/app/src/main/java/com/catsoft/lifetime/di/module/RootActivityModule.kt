package com.catsoft.lifetime.di.module

import com.catsoft.lifetime.ui.screens.root.RootActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class RootActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): RootActivity
}