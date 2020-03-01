package com.catsoft.vktinF.vkApi.model

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class VKPrice(
    val amount : Float,
    val currency: VKCurrency,
    val text : String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readFloat(), parcel.readParcelable(VKCurrency::class.java.classLoader)!!, parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(amount)
        parcel.writeParcelable(currency, flags)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VKPrice> {
        override fun createFromParcel(parcel: Parcel): VKPrice {
            return VKPrice(parcel)
        }

        override fun newArray(size: Int): Array<VKPrice?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject?): VKPrice {
            if (json == null) return VKPrice(0F, VKCurrency(0, "RU"), "")
            return VKPrice(
                amount = json.optInt("amount") / 100F, text = json.optString("text"), currency = VKCurrency.parse(json.optJSONObject("currency"))
            )
        }
    }
}