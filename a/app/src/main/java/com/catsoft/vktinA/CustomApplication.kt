package com.catsoft.vktinA

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.catsoft.vktinA.di.SimpleDi
import com.catsoft.vktinA.services.CurrentLocaleProvider
import com.catsoft.vktinA.vkApi.documents.DocumentsApi
import com.catsoft.vktinA.vkApi.documents.IDocumentsApi
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