package com.c.v

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.c.v.di.AppInjector
import com.c.v.di.SimpleDi
import com.c.v.services.CurrentLocaleProvider
import com.c.v.data.network.vkApi.VkApi
import com.c.v.data.network.vkApi.IVkApi
import com.vk.api.sdk.VK
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CustomApplication : android.app.Application(), HasAndroidInjector {

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)

        VK.initialize(this)

        SimpleDi.Instance.register(IVkApi::class.java, VkApi())
        val locale = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
        SimpleDi.Instance.register(CurrentLocaleProvider::class.java, CurrentLocaleProvider(locale))
    }
}