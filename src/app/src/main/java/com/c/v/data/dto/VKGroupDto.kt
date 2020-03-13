package com.c.v.data.dto

data class VKGroupDto @JvmOverloads constructor (
    var id : Int = 0,
    var name : String = "",
    var screenName : String = "",
    var deactivated : String = "",
    var isMember : Boolean = false,
    var isClosed : Int = 0,
    var photo50 : String = "",
    var photo100 : String = "",
    var photo200 : String = "",
    var isHiddenFromFeed : Boolean = false,
    var status : String = "",
    var members_count : Int = 0,
    var description : String = "")

