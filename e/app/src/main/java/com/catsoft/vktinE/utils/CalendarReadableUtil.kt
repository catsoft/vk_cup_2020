package com.catsoft.vktinE.utils

import com.catsoft.vktinE.di.SimpleDi
import com.catsoft.vktinE.services.CurrentLocaleProvider
import java.text.SimpleDateFormat
import java.util.*

object CalendarReadableUtil {
    fun format(calendar: Calendar): String {

        val locale = SimpleDi.Instance.resolve<CurrentLocaleProvider>(CurrentLocaleProvider::class.java).currentLocale

        val yesterdayDate = Calendar.getInstance()
        yesterdayDate.add(Calendar.DAY_OF_MONTH, -1)

        val now = Calendar.getInstance()

        return when (calendar) {
            now -> "Сегодня"
            yesterdayDate -> "Вчера"
            else -> {
                return (if (calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                    val dateFormat = SimpleDateFormat("d MMM", locale)
                    dateFormat.format(calendar.time)
                } else {
                    val dateFormat = SimpleDateFormat("d MMM YYYY", locale)
                    dateFormat.format(calendar.time)
                }).replace(".", "")
            }
        }
    }
}