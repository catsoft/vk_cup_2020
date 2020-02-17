package com.catsoft.lifetime.ui.base

import android.app.Application
import com.catsoft.lifetime.di.AppInjector
import dagger.android.*
import javax.inject.Inject

class TimeApplication : Application(), HasAndroidInjector {

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
    }

}