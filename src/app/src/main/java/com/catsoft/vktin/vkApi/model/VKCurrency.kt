package com.catsoft.vktin.vkApi.model

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class VKCurrency(
    val id : Int,
    val name : String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(), parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VKCurrency> {
        override fun createFromParcel(parcel: Parcel): VKCurrency {
            return VKCurrency(parcel)
        }

        override fun newArray(size: Int): Array<VKCurrency?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject?): VKCurrency {
            if (json == null) return VKCurrency(0, "")
            return VKCurrency(
                id = json.optInt("id"), name = json.optString("name")
            )
        }
    }
}