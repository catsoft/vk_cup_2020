package com.c.v.ui.unsubscribe_flow.group_list

import android.os.Parcel
import android.os.Parcelable
import com.c.v.data.IWithIdModel

data class VKGroupPresentation @JvmOverloads constructor(
    var name : String ="",
    var screenName : String="",
    var deactivated : String="",
    var isMember : Boolean=false,
    var isClosed : Int = 0,
    var photo50 : String ="",
    var photo100 : String="",
    var photo200 : String="",
    var isHiddenFromFeed : Boolean= false,
    var status : String = "",
    var members_count : Int = 0,
    var description : String = "",
    var isSelected : Boolean = false,
    override var id: Int = 0) : Parcelable, IWithIdModel {
    constructor(parcel: Parcel) : this(
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
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
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
        parcel.writeByte(if (isSelected) 1 else 0)
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