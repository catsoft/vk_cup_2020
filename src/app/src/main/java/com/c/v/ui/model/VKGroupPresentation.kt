package com.c.v.ui.model

import android.os.Parcel
import android.os.Parcelable
import com.c.v.data.IWithIdModel

data class VKGroupPresentation (
    var localId: Int,
    val remoteId: Int,
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
    val members_count : Int,
    val description : String, override val id: Int = remoteId) : Parcelable, IWithIdModel {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
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
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(localId)
        parcel.writeInt(remoteId)
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
        parcel.writeInt(members_count)
        parcel.writeString(description)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VKGroupPresentation> {
        override fun createFromParcel(parcel: Parcel): VKGroupPresentation {
            return VKGroupPresentation(parcel)
        }

        override fun newArray(size: Int): Array<VKGroupPresentation?> {
            return arrayOfNulls(size)
        }
    }
}