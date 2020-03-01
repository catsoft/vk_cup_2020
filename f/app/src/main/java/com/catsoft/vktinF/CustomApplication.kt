package com.catsoft.vktinF

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.catsoft.vktinF.di.SimpleDi
import com.catsoft.vktinF.services.CurrentLocaleProvider
import com.catsoft.vktinF.vkApi.VkApi
import com.catsoft.vktinF.vkApi.IVkApi
import com.vk.api.sdk.VK

class CustomApplication : android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        VK.initialize(this)

        SimpleDi.Instance.register(IVkApi::class.java, VkApi())
        val locale = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
        SimpleDi.Instance.register(CurrentLocaleProvider::class.java, CurrentLocaleProvider(locale))
    }
}