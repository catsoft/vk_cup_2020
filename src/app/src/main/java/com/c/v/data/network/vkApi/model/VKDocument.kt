package com.c.v.data.network.vkApi.model

import android.os.Parcel
import android.os.Parcelable
import com.c.v.data.IWithIdModel
import org.json.JSONObject
import java.util.*

data class VKDocument(
    override val id: Int,
    val ownerId : Int,
    val title : String,
    val size : Int,
    val ext : String,
    val url : String,
    internal val calendar: Calendar,
    val type : VKDocumentType,
    val preview : String,
    val tags : List<String>
) : Parcelable, IWithIdModel {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        getCalendarFromMilliSec(
            parcel.readLong()
        ),
        VKDocumentType.valueOf(parcel.readInt()),
        parcel.readString()!!,
        readStringList(parcel)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(ownerId)
        parcel.writeString(title)
        parcel.writeInt(size)
        parcel.writeString(ext)
        parcel.writeString(url)
        parcel.writeLong(calendar.timeInMillis)
        parcel.writeInt(type.value)
        parcel.writeString(preview)
        parcel.writeStringList(tags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VKDocument> {
        override fun createFromParcel(parcel: Parcel): VKDocument {
            return VKDocument(parcel)
        }

        override fun newArray(size: Int): Array<VKDocument?> {
            return arrayOfNulls(size)
        }

        fun getCalendarFromMilliSec(milliSec: Long): Calendar {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = milliSec

            return calendar
        }

        fun readStringList(parcel: Parcel): List<String> {
            val strList = mutableListOf<String>()
            parcel.readStringList(strList)
            return strList
        }

        private fun getStringList(json: JSONObject): List<String> {
            if (json.has("tags")) {
                val jsonArray = json.getJSONArray("tags")
                val list = mutableListOf<String>()
                for (i in 0 until jsonArray.length()) {
                    list.add(jsonArray.getString(i))
                }
                return list
            }
            return emptyList()
        }

        fun parse(json: JSONObject): VKDocument {
            return VKDocument(
                id = json.optInt("id", 0),
                ownerId = json.optInt("owner_id", 0),
                title = json.optString("title", ""),
                size = json.optInt("size", 0),
                ext = json.optString("ext", ""),
                url = json.optString("url", ""),
                calendar = getCalendarFromMilliSec(
                    json.optLong("date", 0) * 1000
                ),
                type = VKDocumentType.valueOf(
                    json.optInt("type", 0)
                ),
                preview = json.optJSONObject("preview")?.optJSONObject("photo")
                    ?.optJSONArray("sizes")?.let {
                    if (it.length() > 0) {
                        it.optJSONObject(0)?.optString("src")
                    } else {
                        ""
                    }
                } ?: "",
                tags = getStringList(
                    json
                )
            )
        }
    }
}

