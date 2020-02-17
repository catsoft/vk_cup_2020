package com.catsoft.vktinA

import com.vk.api.sdk.VK

class CustomApplication : android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        VK.initialize(this)
    }
}