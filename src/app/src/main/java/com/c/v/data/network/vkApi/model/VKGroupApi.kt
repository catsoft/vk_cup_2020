package com.c.v.data.network.vkApi.model

import android.os.Parcel
import android.os.Parcelable
import com.c.v.data.IWithIdModel
import org.json.JSONObject

data class VKGroupApi (
    override val id: Int,
    val name : String,
    val screenName : String,
    val deactivated : String,
    val isMember : Boolean,
    val isClosed : Int,
    val photo50 : String,
    val photo100 : String,
    val photo200 : String,
    val isHiddenFromFeed : Boolean,
    val status : String,
    val city : VKCity,
    val members_count : Int,
    val description : String,
    val market : VKMarket) : Parcelable, IWithIdModel {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readParcelable(VKCity::class.java.classLoader)!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readParcelable(VKMarket::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(screenName)
        parcel.writeString(deactivated)
        parcel.writeByte(if (isMember) 1 else 0)
        parcel.writeInt(isClosed)
        parcel.writeString(photo50)
        parcel.writeString(photo100)
        parcel.writeString(photo200)
        parcel.writeByte(if (isHiddenFromFeed) 1 else 0)
        parcel.writeString(status)
        parcel.writeParcelable(city, flags)
        parcel.writeInt(members_count)
        parcel.writeString(description)
        parcel.writeParcelable(market, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VKGroupApi> {
        override fun createFromParcel(parcel: Parcel): VKGroupApi {
            return VKGroupApi(parcel)
        }

        override fun newArray(size: Int): Array<VKGroupApi?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject): VKGroupApi {
            return VKGroupApi(
                id = json.optInt("id"),
                name = json.optString("name"),
                screenName = json.optString("screen_name"),
                deactivated = json.optString("deactivated"),
                isClosed = json.optInt("is_closed"),
                isMember = json.optInt("is_member") == 1,
                photo50 = json.optString("photo_50"),
                photo100 = json.optString("photo_100"),
                photo200 = json.optString("photo_200"),
                isHiddenFromFeed = json.optInt("is_hidden_from_feed") == 1,
                status = json.optString("status"),
                members_count = json.optInt("members_count"),
                description = json.optString("description"),
                city = VKCity.parse(json.optJSONObject("city")),
                market = VKMarket.parse(json.optJSONObject("market"))
            )
        }
    }
}