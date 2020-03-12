package com.c.v.data.network.vkApi.model

import android.os.Parcel
import android.os.Parcelable
import com.c.v.data.IWithIdModel
import org.json.JSONObject

data class VKCity(override val id: Int, val title: String) : Parcelable, IWithIdModel {
    constructor(parcel: Parcel) : this(
        parcel.readInt(), parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VKCity> {
        override fun createFromParcel(parcel: Parcel): VKCity {
            return VKCity(parcel)
        }

        override fun newArray(size: Int): Array<VKCity?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject?): VKCity {
            if (json == null) return VKCity(0, "")
            return VKCity(
                id = json.optInt("id", 0), title = json.optString("title", "")
            )
        }
    }
}