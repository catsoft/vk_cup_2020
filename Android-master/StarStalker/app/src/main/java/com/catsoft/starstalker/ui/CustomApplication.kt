package com.catsoft.starstalker.ui

import com.vk.sdk.VKSdk

class CustomApplication : android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        VKSdk.initialize(this)
    }
}