package com.catsoft.vktinG

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.catsoft.vktinG.di.SimpleDi
import com.catsoft.vktinG.services.CurrentLocaleProvider
import com.catsoft.vktinG.vkApi.VkApi
import com.catsoft.vktinG.vkApi.IVkApi
import com.vk.api.sdk.VK

/**
 * Проблемы
 * при клике на элемент не кликается по заголовку из-за editTextView
 * нет рефрешера
 * нет нормального оповещения об ошибках
 * нет viewModelProvider из за этого пересоздание фракгмента это боль
 * нет popupService из за этого в коде вью модели есть обращение к вью
 * нет состояния загрузки
 * нет обработки медленного интернета
 */
class CustomApplication : android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        VK.initialize(this)

        SimpleDi.Instance.register(IVkApi::class.java, VkApi())
        val locale = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
        SimpleDi.Instance.register(CurrentLocaleProvider::class.java, CurrentLocaleProvider(locale))
    }
}