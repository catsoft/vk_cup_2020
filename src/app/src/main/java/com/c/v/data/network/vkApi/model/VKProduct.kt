package com.c.v.data.network.vkApi.model

import android.os.Parcel
import android.os.Parcelable
import com.c.v.data.IWithIdModel
import org.json.JSONObject

data class VKProduct(
    override val id: Int,
    val ownerId : Int,
    val title : String,
    val description : String,
    val price : VKPrice,
//    val category : VKCategory
    val thumb_photo : String,
//    val date : Int,
    val availability : Int,
    val isFavorite : Boolean
) : Parcelable, IWithIdModel {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(VKPrice::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(ownerId)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(thumb_photo)
        parcel.writeInt(availability)
        parcel.writeByte(if (isFavorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VKProduct> {
        override fun createFromParcel(parcel: Parcel): VKProduct {
            return VKProduct(parcel)
        }

        override fun newArray(size: Int): Array<VKProduct?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject?): VKProduct {
            if (json == null) return VKProduct(0, 0, "", "", VKPrice(0F, VKCurrency(0, "RU"), ""), "", 0, false)
            return VKProduct(
                id = json.optInt("id"),
                ownerId = json.optInt("owner_id"),
                title = json.optString("title"),
                description = json.optString("description"),
                price = VKPrice.parse(json.optJSONObject("price")),
                thumb_photo = json.optString("thumb_photo"),
                availability = json.optInt("availability"),
                isFavorite = json.optBoolean("is_favorite")
            )
        }
    }
}