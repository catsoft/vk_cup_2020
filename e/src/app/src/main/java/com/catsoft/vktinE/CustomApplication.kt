package com.catsoft.vktinE

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.catsoft.vktinE.di.SimpleDi
import com.catsoft.vktinE.services.CurrentLocaleProvider
import com.catsoft.vktinE.vkApi.IVKWallApi
import com.catsoft.vktinE.vkApi.VKWallApi
import com.vk.api.sdk.VK

class CustomApplication : android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        VK.initialize(this)

        SimpleDi.Instance.register(IVKWallApi::class.java, VKWallApi())
        val locale = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
        SimpleDi.Instance.register(CurrentLocaleProvider::class.java, CurrentLocaleProvider(locale))
    }
}