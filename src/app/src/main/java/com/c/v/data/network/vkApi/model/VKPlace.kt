package com.c.v.data.network.vkApi.model

import com.c.v.data.IWithIdModel
import org.json.JSONObject
import java.util.*

data class VKPlace(val date: Calendar, override val id: Int) : IWithIdModel {

    companion object {

        fun parse(jsonObject: JSONObject) : VKPlace {
            return VKPlace(
                getCalendarFromMilliSec(jsonObject.optLong("date") * 1000),
                0
            )
        }

        private fun getCalendarFromMilliSec(milliSec: Long): Calendar {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = milliSec

            return calendar
        }
    }
}