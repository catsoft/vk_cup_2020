package com.c.v.data.network.vkApi.model

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class VKMarket(
    val enabled : Boolean,
    val priceMin : Int,
    val priceMax : Int,
    val mainAlbumId : Int,
    val contactId : Int,
    val currencyText : String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (enabled) 1 else 0)
        parcel.writeInt(priceMin)
        parcel.writeInt(priceMax)
        parcel.writeInt(mainAlbumId)
        parcel.writeInt(contactId)
        parcel.writeString(currencyText)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VKMarket> {
        override fun createFromParcel(parcel: Parcel): VKMarket {
            return VKMarket(parcel)
        }

        override fun newArray(size: Int): Array<VKMarket?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject?): VKMarket {
            if (json == null) return VKMarket(false, 0, 0, 0, 0, "")
            return VKMarket(
                enabled = json.optInt("enabled") == 1,
                priceMax = json.optInt("price_max", 0),
                priceMin = json.optInt("price_min", 0),
                mainAlbumId = json.optInt("main_album_id", 0),
                contactId = json.optInt("contact_id", 0),
                currencyText = json.optString("currency_text", "Нет значения?")
            )
        }
    }
}
