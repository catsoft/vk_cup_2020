package com.catsoft.vktin

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.catsoft.vktin.di.SimpleDi
import com.catsoft.vktin.services.CurrentLocaleProvider
import com.catsoft.vktin.vkApi.documents.DocumentsApi
import com.catsoft.vktin.vkApi.documents.IDocumentsApi
import com.vk.api.sdk.VK

class CustomApplication : android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        VK.initialize(this)

        SimpleDi.Instance.register(IDocumentsApi::class.java, DocumentsApi())
        val locale = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
        SimpleDi.Instance.register(CurrentLocaleProvider::class.java, CurrentLocaleProvider(locale))
    }
}